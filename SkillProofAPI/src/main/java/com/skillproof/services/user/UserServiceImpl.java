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
//    private final SkillsAndExperienceDao skillsAndExperienceDao;
//    private final CommentDao commentDao;
//    private final NotificationDao notificationDao;
//    private final ConnectionDao connectionDao;
//    private final InterestReactionDao interestReactionDao;
//    private final RoleDao roleDao;

    @Autowired
    private final BCryptPasswordEncoder encoder;


    /* --------- NETWORK --------- */

//    public void newConnection(User user, Long userFollowingId) {
//        User userToBeFollowed = getUserById(userFollowingId);
//        Connection newConnection = new Connection(userToBeFollowed);
//        Notification notification = new Notification(NotificationType.CONNECTION_REQUEST, userToBeFollowed, newConnection);
//        notificationDao.save(notification);
//        connectionDao.save(newConnection);
//    }

    /* --------- FEED --------- */

//    public Set<Post> getFeedPosts(User user) {
//        Set<Post> feedPosts = new HashSet<>();

//        Collection<Connection> connections = user.getUsers();
//        for (Connection con : connections) {
//            if (con.getIsAccepted()) {
//                User userFollowing = con.getUser();
//                feedPosts.addAll(userFollowing.getPosts());
//                Collection<InterestReaction> interestReactions = userFollowing.getInterestReactions();
//                for (InterestReaction ir : interestReactions) {
//                    feedPosts.add(ir.getPost());
//                }
//            }
//        }
//        connections = user.getUsers();
//        for (Connection con : connections) {
//            if (con.getIsAccepted()) {
//                User userFollowing = con.getUser();
//                feedPosts.addAll(userFollowing.getPosts());
//                Collection<InterestReaction> interestReactions = userFollowing.getInterestReactions();
//                for (InterestReaction ir : interestReactions) {
//                    feedPosts.add(ir.getPost());
//                }
//            }
//        }
//
//        for (Post p : feedPosts) {
//            User owner = p.getUser();
//
////            Picture pic = owner.getProfilePicture();
////            if (pic != null) {
////                if (pic.isCompressed()) {
////                    Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
////                    pic.setCompressed(false);
////                    owner.setProfilePicture(tempPicture);
////                }
////            }
//
////            Collection<Comment> comments = p.getComments();
////            for (Comment c : comments) {
////                User commentOwner = c.getUserMadeBy();
////                Picture cpic = commentOwner.getProfilePicture();
////                if (cpic != null) {
////                    if (cpic.isCompressed()) {
////                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(), PictureSave.decompressBytes(cpic.getBytes()));
////                        cpic.setCompressed(false);
////                        commentOwner.setProfilePicture(tempPicture);
////                    }
////                }
////            }
//
//            Set<Picture> newPicts = new HashSet<>();
//            for (Picture pict : p.getPictures()) {
//                if (pict != null) {
//                    if (pict.isCompressed()) {
//                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), PictureSave.decompressBytes(pict.getBytes()));
//                        tempPicture.setCompressed(false);
//                        newPicts.add(tempPicture);
//                    } else
//                        newPicts.add(pict);
//                }
//            }
//            p.setPictures(newPicts);
//        }
//        return feedPosts;
//    }

    /*public void newPostInterested(User user, Post post) {
        InterestReaction newReaction = new InterestReaction(user, post);
        User postOwner = post.getUser();
        if (postOwner != user) {
            Notification notification = new Notification(NotificationType.INTEREST, postOwner, newReaction);
            notificationDao.save(notification);
        }
        interestReactionDao.save(newReaction);
    }*/

    /*public void newPostComment(User user, Post post, Comment comment) {
        comment.setUserMadeBy(user);
        comment.setPost(post);
        User postOwner = post.getUser();
        if (postOwner != user) {
            Notification notification = new Notification(NotificationType.COMMENT, postOwner, comment);
            notificationDao.save(notification);
        }
        commentDao.save(comment);
    }*/


//    public Set<User> getUserNetwork(User currentUser) {
//        Set<User> network = new HashSet<>();

//        Collection<Connection> connectionsFollowing = currentUser.getUsers();
//        for (Connection con : connectionsFollowing) {
//            if (con.getIsAccepted()) {
//                User userinNetwork = con.getUser();
//                network.add(userinNetwork);
//            }
//        }
//
//        Collection<Connection> connectionsFollowedBy = currentUser.getUsers();
//        for (Connection con : connectionsFollowedBy) {
//            if (con.getIsAccepted()) {
//                User userinNetwork = con.getUser();
//                network.add(userinNetwork);
//            }
//        }

//        for (User user : network) {
//            Picture uPic = user.getProfilePicture();
//            if (uPic != null && uPic.isCompressed()) {
//                Picture temp = new Picture(uPic.getName(), uPic.getType(), PictureSave.decompressBytes(uPic.getBytes()));
//                user.setProfilePicture(temp);
//            }
//        }
//        return network;
//    }

    /*public List<User> getUsersWithOutAdmin() {
        Set<User> usersWithoutAdmin = new HashSet<>();
//        List<User> users = userDao.findAll();
//        for (User user : users) {
//            if (!user.getUserName().equals("admin")) {
//                usersWithoutAdmin.add(user);
//            }
//        }
        return new ArrayList<>(usersWithoutAdmin);
    }

    public Integer hasApplied(User u, Job j) {
//        for (Job jj : u.getJobApplied()) {
//            if (jj.getId() == j.getId())
//                return 1;
//        }
//
//        for (Job jj : u.getJobsCreated()) {
//            if (jj.getId() == j.getId())
//                return 0;
//        }
        return -1;
    }

    public boolean hasLiked(User u, Post p) {
//        for (InterestReaction i : u.getInterestReactions()) {
//            if (i.getPost() == p)
//                return true;
//        }
        return false;
    }

    public Integer numOfComments(User u, Post p) {
        Integer numOfComments = 0;
//        for (Comment c : u.getComments()) {
//            if (c.getUserMadeBy() == u)
//                numOfComments++;
//        }
        return numOfComments;
    }*/

    /*public Integer matchingSkills(User u, Job j) {
        Collection<SkillsAndExperience> skills = new ArrayList<>();
        Integer avgDistance = 0;
        for (SkillsAndExperience s : skills) {
            Integer editDist = Utils.minDistance(s.getDescription().toLowerCase(), j.getTitle().toLowerCase());
            avgDistance += editDist;
        }
//        if (u.getCurrentJob() != null)
//            avgDistance += Utils.minDistance(u.getCurrentJob().toLowerCase(), j.getTitle().toLowerCase());
        if (avgDistance != 0) {
            return (int) (((double) avgDistance) / ((double) skills.size()));
        } else {
            return -1;
        }
    }*/

  /*  public Integer matchingSkills(User u, Post p) {
        Collection<SkillsAndExperience> skills = new ArrayList<>();
        Integer avgDistance = 0;
        for (SkillsAndExperience s : skills) {
            Integer editDist = Utils.minDistance(s.getDescription().toLowerCase(), p.getContent().toLowerCase());
            avgDistance += editDist;
        }

//        if (u.getCurrentJob() != null)
//            avgDistance += Utils.minDistance(u.getCurrentJob().toLowerCase(), p.getContent().toLowerCase());

        if (avgDistance != 0) {
            return (int) (((double) avgDistance) / ((double) skills.size()));
        } else {
            return -1;
        }
    }*/

    /*public boolean isNetworkPost(User u, Post p) {
        for (Post pp : getFeedPosts(u)) {
            if (pp == p)
                return true;
        }
        return false;
    }*/

    public UserResponse getUserById(String id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException(ObjectConstants.ID, id);
        }
        return getUserResponse(user);
    }

//    @Override
//    public User signup(User user, MultipartFile file) throws IOException {
//        User existingUser = findUserByEmailAddress(user.getEmailAddress());
//
//        return null;
//    }

//    @Override
//    public User getProfile(Long id) {
//        User user = getUserById(id);
//        Picture pic = user.getProfilePicture();
//        if (pic != null && pic.isCompressed()) {
//            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
//            tempPicture.setCompressed(false);
//            user.setProfilePicture(tempPicture);
//        }
//        return user;
//    }

//    @Override
//    public User getPersonalProfile(Long id, Long otherId) {
//        User user = getUserById(otherId);
//        Picture pic = user.getProfilePicture();
//        if (pic != null && pic.isCompressed()) {
//            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
//            pic.setCompressed(false);
//            user.setProfilePicture(tempPicture);
//        }
//        return user;
//    }

//    @Override
//    public void informPersonalProfile(Long id, SkillsAndExperience skill) {
//        User user = getUserById(id);
//        if (skill.getSkillType() == SkillType.EXPERIENCE) {
//            skill.setUser(user);
//        } else if (skill.getSkillType() == SkillType.SKILL) {
//            skill.setUser(user);
//        } else if (skill.getSkillType() == SkillType.EDUCATION) {
//            skill.setUser(user);
//        }
//        skillsAndExperienceDao.save(skill);
//    }

//    @Override
//    public void editUserJob(Long id, User user) {
//        User newUser = getUserById(id);
//        if (user.getCurrentJob() != null)
//            newUser.setCurrentJob(user.getCurrentJob());
//        if (user.getCurrentCompany() != null)
//            newUser.setCurrentCompany(user.getCurrentCompany());
//        if (user.getCity() != null)
//            newUser.setCity(user.getCity());
//        if (user.getWebsite() != null)
//            newUser.setWebsite(user.getWebsite());
//        if (user.getGithub() != null)
//            newUser.setGithub(user.getGithub());
//        if (user.getTwitter() != null)
//            newUser.setTwitter(user.getTwitter());
//        userDao.save(newUser);
//    }

//    @Override
//    public User showProfile(Long id, Long otherUserId) {
//        getUserById(id);
//        User userPreview = getUserById(otherUserId);
//        Picture pic = userPreview.getProfilePicture();
//        if (pic != null && pic.isCompressed()) {
//            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
//            userPreview.setProfilePicture(tempPicture);
//            pic.setCompressed(false);
//        }
//        return userPreview;
//    }

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
        user.setCity(createUserRequest.getCity());
        user.setPassword(encoder.encode(createUserRequest.getPassword()));
        user.setRole(createUserRequest.getRole());
        if (CollectionUtils.isNotEmpty(createUserRequest.getSkills())) {
            user.setSkills(StringUtils.join(createUserRequest.getSkills(), ","));
        }
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
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
        if (StringUtils.isNotEmpty(updateUserRequest.getPhone())){
            user.setPhone(updateUserRequest.getPhone());
        }
        if (CollectionUtils.isNotEmpty(updateUserRequest.getSkills())){
            user.setSkills(StringUtils.join(updateUserRequest.getSkills(), ","));
        }
        user.setUpdatedDate(LocalDateTime.now());
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

    private List<UserResponse> getResponseListWithAdmin(List<User> users) {
        return users.stream()
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

