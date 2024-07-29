package com.skillproof.services.user;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.constants.UserConstants;
import com.skillproof.enums.RoleType;
import com.skillproof.exceptions.ResourceFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.education.EducationResponse;
import com.skillproof.model.request.experience.ExperienceResponse;
import com.skillproof.model.request.profile.UserProfile;
import com.skillproof.model.request.user.CreateUserRequest;
import com.skillproof.model.request.user.UpdateUserRequest;
import com.skillproof.model.request.user.UserResponse;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.AWSS3Service;
import com.skillproof.services.education.EducationService;
import com.skillproof.services.experience.ExperienceService;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final ExperienceService experienceService;
    private final EducationService educationService;
    private final AWSS3Service awss3Service;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder,
                           ExperienceService experienceService, EducationService educationService,
                           AWSS3Service awss3Service) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.experienceService = experienceService;
        this.educationService = educationService;
        this.awss3Service = awss3Service;
    }

    public UserResponse getUserById(String id) {
        LOG.debug("Start of getUserById method - UserServiceImpl");
        User user = userRepository.getUserById(id);
        if (user == null) {
            LOG.error("User with id {} not found.", id);
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }
        LOG.debug("End of getUserById method - UserServiceImpl");
        return getUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmailAddress(String emailAddress) {
        LOG.debug("Start of getUserById method - UserServiceImpl");
        User user = userRepository.getUserByUsername(emailAddress);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with emailAddress {} not found.", emailAddress);
            throw new UserNotFoundException(UserConstants.USER_EMAIL, emailAddress);
        }
        LOG.debug("End of getUserById method - UserServiceImpl");
        return getUserResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        LOG.debug("Start of getUserById method - UserServiceImpl");
        User existingUser = userRepository.getUserByUsername(createUserRequest.getEmailAddress());
        if (ObjectUtils.isNotEmpty(existingUser)) {
            throw new ResourceFoundException(ObjectConstants.USER, UserConstants.USER_EMAIL, createUserRequest.getEmailAddress());
        }
        User user = createUserEntity(createUserRequest);
        user = userRepository.createUser(user);
        LOG.debug("End of getUserById method - UserServiceImpl");
        return getUserResponse(user);
    }

    @Override
    public List<UserResponse> listAllUsers() {
        LOG.debug("Start of listAllUsers method - UserServiceImpl");
        List<User> users = userRepository.listAllUsers();
        return getResponseList(users, false);
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest updateUserRequest) {
        LOG.debug("Start of updateUser method - UserServiceImpl");
        User user = userRepository.getUserById(id);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", id);
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }
        prepareUserEntity(user, updateUserRequest);
        User updatedUser = userRepository.updateUser(user);
        LOG.debug("End of updateUser method - UserServiceImpl");
        return getUserResponse(updatedUser);
    }

    @Override
    public UserProfile updateProfilePicture(String id, MultipartFile profilePicture) throws Exception {
        LOG.debug("Start of updateProfilePicture method - UserServiceImpl");
        User user = userRepository.getUserById(id);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", id);
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }

        String oldProfilePictureUrl = user.getProfilePicture();

        // Delete the old profile picture from S3
        if (oldProfilePictureUrl != null && !oldProfilePictureUrl.isEmpty()) {
            LOG.debug("Deleting old profile picture");
            awss3Service.deleteFile(oldProfilePictureUrl);
        }

        String profilePictureUrl = null;
        if (ObjectUtils.isNotEmpty(profilePicture)) {
            LOG.debug("Uploading new profile picture");
            profilePictureUrl = awss3Service.uploadFile(profilePicture);
        }
        user.setProfilePicture(profilePictureUrl);
        userRepository.updateUser(user);
        return getUserProfileByUserId(id);
    }

    @Override
    public void deleteUserById(String id) {
        LOG.debug("Start of deleteUserById method - UserServiceImpl");
        User user = userRepository.getUserById(id);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", id);
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }
        userRepository.deleteUserById(id);
        LOG.debug("End of deleteUserById method - UserServiceImpl");
    }

    @Override
    public List<UserResponse> listUsersByRole(RoleType role) {
        LOG.debug("Start of listUsersByRole method - UserServiceImpl");
        List<User> users = userRepository.listUsersByRole(role);
        return getResponseList(users, true);
    }

    @Override
    public UserProfile getUserProfileByUserId(String id) {
        LOG.debug("Start of getUserProfileByUserId method - UserServiceImpl");
        UserResponse user = getUserById(id);
        List<ExperienceResponse> experiences = experienceService.getExperienceByUserId(id);
        List<EducationResponse> educationDetails = educationService.getEducationByUserId(id);
        return getUserProfile(user, experiences, educationDetails);
    }

    private UserProfile getUserProfile(UserResponse user, List<ExperienceResponse> experiences,
                                       List<EducationResponse> educationDetails) {
        LOG.debug("Start of getUserProfile method - UserServiceImpl");
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setExperiences(experiences);
        userProfile.setEducationDetails(educationDetails);
        LOG.debug("End of getUserProfile method - UserServiceImpl");
        return userProfile;
    }

    private User createUserEntity(CreateUserRequest createUserRequest) {
        LOG.debug("Start of createUserEntity method - UserServiceImpl");
        User user = new User();
        user.setId(getRandomUUID());
        user.setEmailAddress(createUserRequest.getEmailAddress());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setPhone(createUserRequest.getPhone());
        user.setBio(createUserRequest.getBio());
        user.setCity(createUserRequest.getCity());
        user.setPassword(encoder.encode(createUserRequest.getPassword()));
        user.setRole(createUserRequest.getRole());
        if (CollectionUtils.isNotEmpty(createUserRequest.getSkills())) {
            user.setSkills(StringUtils.join(createUserRequest.getSkills(), ","));
        }
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        LOG.debug("End of createUserEntity method - UserServiceImpl");
        return user;
    }

    private void prepareUserEntity(User user, UpdateUserRequest updateUserRequest) {
        LOG.debug("Start of prepareUserEntity method - UserServiceImpl");
        if (StringUtils.isNotEmpty(updateUserRequest.getFirstName())) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if (StringUtils.isNotEmpty(updateUserRequest.getLastName())) {
            user.setLastName(updateUserRequest.getLastName());
        }
        if (StringUtils.isNotEmpty(updateUserRequest.getCity())) {
            user.setCity(updateUserRequest.getCity());
        }
        if (StringUtils.isNotEmpty(updateUserRequest.getPhone())) {
            user.setPhone(updateUserRequest.getPhone());
        }
        if (StringUtils.isNotEmpty(updateUserRequest.getBio())) {
            user.setBio(updateUserRequest.getBio());
        }
        if (CollectionUtils.isNotEmpty(updateUserRequest.getSkills())) {
            user.setSkills(StringUtils.join(updateUserRequest.getSkills(), ","));
        }
        user.setUpdatedDate(LocalDateTime.now());
        LOG.debug("End of prepareUserEntity method - UserServiceImpl");
    }

    private String getRandomUUID() {
        return UserConstants.COMMON_USERID_PREFIX + RandomStringUtils.randomAlphanumeric(18);
    }

    private List<UserResponse> getResponseList(List<User> users, boolean includeAdmins) {
        return users.stream()
                .filter(entity -> {
                    if (includeAdmins) {
                        return true;
                    }
                    return entity.getRole() != RoleType.ADMIN;
                })
                .map(this::getUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse getUserResponse(User user) {
        UserResponse response = ResponseConverter.copyProperties(user, UserResponse.class);
        if (StringUtils.isNotEmpty(user.getSkills())) {
            response.setSkills(Arrays.asList(user.getSkills().split(",")));
        }
        response.setPassword(null);
        return response;
    }
}

