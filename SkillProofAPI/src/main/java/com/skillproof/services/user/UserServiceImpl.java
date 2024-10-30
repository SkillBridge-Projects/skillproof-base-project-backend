package com.skillproof.services.user;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.constants.UserConstants;
import com.skillproof.enums.RoleType;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.user.CreateUserRequest;
import com.skillproof.model.request.user.UpdateUserRequest;
import com.skillproof.model.request.user.UserResponse;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.exceptions.ResourceFoundException;
import com.skillproof.utils.ResponseConverter;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    public UserResponse getUserById(String id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }
        return getUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmailAddress(String emailAddress) {
        User user = userRepository.getUserByUsername(emailAddress);
        if (ObjectUtils.isEmpty(user)) {
            throw new UserNotFoundException(UserConstants.USER_EMAIL, emailAddress);
        }
        return getUserResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User existingUser = userRepository.getUserByUsername(createUserRequest.getEmailAddress());
        if (ObjectUtils.isNotEmpty(existingUser)) {
            throw new ResourceFoundException(ObjectConstants.USER, UserConstants.USER_EMAIL, createUserRequest.getEmailAddress());
        }
        User user = new User();
        String userId = RandomStringUtils.randomAlphanumeric(20);
        user.setId(userId);
        user.setEmailAddress(createUserRequest.getEmailAddress());
        user.setFirstName(createUserRequest.getFirstName());
        user.setLastName(createUserRequest.getLastName());
        user.setPhone(createUserRequest.getPhone());
        user.setBio(createUserRequest.getBio());
        user.setCity(createUserRequest.getCity());
        user.setPassword(encoder.encode(createUserRequest.getPassword()));
        user.setRole(createUserRequest.getRole());
//        user.setCreatedDate(LocalDateTime.now());
//        user.setUpdatedDate(LocalDateTime.now());
        user = userRepository.createUser(user);
        return getUserResponse(user);
    }

    @Override
    public List<UserResponse> listAllUsers() {
        List<User> users = userRepository.listAllUsers();
        return getResponseList(users, false);
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.getUserById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }
        prepareUserEntity(user, updateUserRequest);
        User updatedUser = userRepository.updateUser(user);
        return getUserResponse(updatedUser);
    }

    private void prepareUserEntity(User user, UpdateUserRequest updateUserRequest) {
        if (StringUtils.isNotEmpty(updateUserRequest.getFirstName())){
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if (StringUtils.isNotEmpty(updateUserRequest.getLastName())){
            user.setLastName(updateUserRequest.getLastName());
        }
        if (StringUtils.isNotEmpty(updateUserRequest.getCity())){
            user.setCity(updateUserRequest.getCity());
        }
        if (updateUserRequest.getPhone() != null && updateUserRequest.getPhone() > 0) {
            user.setPhone(updateUserRequest.getPhone());
        }

        if (StringUtils.isNotEmpty(updateUserRequest.getBio())){
            user.setBio(updateUserRequest.getBio());
        }
//        user.setUpdatedDate(LocalDateTime.now());
    }

    @Override
    public void deleteUserById(String id) {
        User user = userRepository.getUserById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }
        userRepository.deleteUserById(id);
    }

    @Override
    public List<UserResponse> listUsersByRole(RoleType role) {
        List<User> users = userRepository.listUsersByRole(role);
        return getResponseList(users, true);
    }

    private List<UserResponse> getResponseList(List<User> users, boolean includeAdmins) {
        return users.stream()
                .filter(entity -> {
                    if (includeAdmins){
                        return true;
                    }
                    return entity.getRole() != RoleType.ADMIN;
                })
                .map(this::getUserResponse)
                .collect(Collectors.toList());
    }

    private UserResponse getUserResponse(User user) {
        UserResponse response = ResponseConverter.copyProperties(user, UserResponse.class);
        response.setPassword(null);
        return response;
    }
}

