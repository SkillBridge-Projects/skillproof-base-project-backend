package com.skillproof.skillproofapi.services.user;

import com.skillproof.skillproofapi.constants.ObjectConstants;
import com.skillproof.skillproofapi.constants.UserConstants;
import com.skillproof.skillproofapi.enumerations.NotificationType;
import com.skillproof.skillproofapi.enumerations.RoleType;
import com.skillproof.skillproofapi.enumerations.SkillType;
import com.skillproof.skillproofapi.exceptions.ResourceFoundException;
import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
import com.skillproof.skillproofapi.model.entity.*;
import com.skillproof.skillproofapi.model.request.user.CreateUserRequest;
import com.skillproof.skillproofapi.model.request.user.UserResponse;
import com.skillproof.skillproofapi.repositories.*;
import com.skillproof.skillproofapi.repositories.user.UserRepository;
import com.skillproof.skillproofapi.utils.PictureSave;
import com.skillproof.skillproofapi.utils.ResponseConverter;
import com.skillproof.skillproofapi.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserRepository userRepository;
    private final SkillsAndExperienceDao skillsAndExperienceDao;
    private final CommentDao commentDao;
    private final NotificationDao notificationDao;
    private final ConnectionDao connectionDao;
    private final InterestReactionDao interestReactionDao;
    private final RoleDao roleDao;

    @Autowired
    private final BCryptPasswordEncoder encoder;


    /* --------- NETWORK --------- */

    public void newConnection(User user, Long userFollowingId) {
        User userToBeFollowed = getUserById(userFollowingId);
        Connection newConnection = new Connection(userToBeFollowed);
        Notification notification = new Notification(NotificationType.CONNECTION_REQUEST, userToBeFollowed, newConnection);
        notificationDao.save(notification);
        connectionDao.save(newConnection);
    }

    /* --------- FEED --------- */

    public Set<Post> getFeedPosts(User user) {
        Set<Post> feedPosts = new HashSet<>(user.getPosts());

        Collection<Connection> connections = user.getUsers();
        for (Connection con : connections) {
            if (con.getIsAccepted()) {
                User userFollowing = con.getUser();
                feedPosts.addAll(userFollowing.getPosts());
                Collection<InterestReaction> interestReactions = userFollowing.getInterestReactions();
                for (InterestReaction ir : interestReactions) {
                    feedPosts.add(ir.getPost());
                }
            }
        }
        connections = user.getUsers();
        for (Connection con : connections) {
            if (con.getIsAccepted()) {
                User userFollowing = con.getUser();
                feedPosts.addAll(userFollowing.getPosts());
                Collection<InterestReaction> interestReactions = userFollowing.getInterestReactions();
                for (InterestReaction ir : interestReactions) {
                    feedPosts.add(ir.getPost());
                }
            }
        }

        for (Post p : feedPosts) {
            User owner = p.getUser();

//            Picture pic = owner.getProfilePicture();
//            if (pic != null) {
//                if (pic.isCompressed()) {
//                    Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
//                    pic.setCompressed(false);
//                    owner.setProfilePicture(tempPicture);
//                }
//            }

//            Collection<Comment> comments = p.getComments();
//            for (Comment c : comments) {
//                User commentOwner = c.getUserMadeBy();
//                Picture cpic = commentOwner.getProfilePicture();
//                if (cpic != null) {
//                    if (cpic.isCompressed()) {
//                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(), PictureSave.decompressBytes(cpic.getBytes()));
//                        cpic.setCompressed(false);
//                        commentOwner.setProfilePicture(tempPicture);
//                    }
//                }
//            }

            Set<Picture> newPicts = new HashSet<>();
            for (Picture pict : p.getPictures()) {
                if (pict != null) {
                    if (pict.isCompressed()) {
                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), PictureSave.decompressBytes(pict.getBytes()));
                        tempPicture.setCompressed(false);
                        newPicts.add(tempPicture);
                    } else
                        newPicts.add(pict);
                }
            }
            p.setPictures(newPicts);
        }
        return feedPosts;
    }

    public void newPostInterested(User user, Post post) {
        InterestReaction newReaction = new InterestReaction(user, post);
        User postOwner = post.getUser();
        if (postOwner != user) {
            Notification notification = new Notification(NotificationType.INTEREST, postOwner, newReaction);
            notificationDao.save(notification);
        }
        interestReactionDao.save(newReaction);
    }

    public void newPostComment(User user, Post post, Comment comment) {
        comment.setUserMadeBy(user);
        comment.setPost(post);
        User postOwner = post.getUser();
        if (postOwner != user) {
            Notification notification = new Notification(NotificationType.COMMENT, postOwner, comment);
            notificationDao.save(notification);
        }
        commentDao.save(comment);
    }


    public Set<User> getUserNetwork(User currentUser) {
        Set<User> network = new HashSet<>();

        Collection<Connection> connectionsFollowing = currentUser.getUsers();
        for (Connection con : connectionsFollowing) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUser();
                network.add(userinNetwork);
            }
        }

        Collection<Connection> connectionsFollowedBy = currentUser.getUsers();
        for (Connection con : connectionsFollowedBy) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUser();
                network.add(userinNetwork);
            }
        }

//        for (User user : network) {
//            Picture uPic = user.getProfilePicture();
//            if (uPic != null && uPic.isCompressed()) {
//                Picture temp = new Picture(uPic.getName(), uPic.getType(), PictureSave.decompressBytes(uPic.getBytes()));
//                user.setProfilePicture(temp);
//            }
//        }
        return network;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public List<User> getUsersWithOutAdmin() {
        Set<User> usersWithoutAdmin = new HashSet<>();
        List<User> users = userDao.findAll();
        for (User user : users) {
            if (!user.getUserName().equals("admin")) {
                usersWithoutAdmin.add(user);
            }
        }
        return new ArrayList<>(usersWithoutAdmin);
    }

    public Integer hasApplied(User u, Job j) {
        for (Job jj : u.getJobApplied()) {
            if (jj.getId() == j.getId())
                return 1;
        }

        for (Job jj : u.getJobsCreated()) {
            if (jj.getId() == j.getId())
                return 0;
        }
        return -1;
    }

    public boolean hasLiked(User u, Post p) {
        for (InterestReaction i : u.getInterestReactions()) {
            if (i.getPost() == p)
                return true;
        }
        return false;
    }

    public Integer numOfComments(User u, Post p) {
        Integer numOfComments = 0;
        for (Comment c : u.getComments()) {
            if (c.getUserMadeBy() == u)
                numOfComments++;
        }
        return numOfComments;
    }

    public Integer matchingSkills(User u, Job j) {
        Collection<SkillsAndExperience> skills = u.getSkillsAndExperiences();
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
    }

    public Integer matchingSkills(User u, Post p) {
        Collection<SkillsAndExperience> skills = u.getSkillsAndExperiences();
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
    }

    public boolean isNetworkPost(User u, Post p) {
        for (Post pp : getFeedPosts(u)) {
            if (pp == p)
                return true;
        }
        return false;
    }

    public User getUserById(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null){
            throw new UserNotFoundException(ObjectConstants.USER, id);
        }
        return user;
    }

    @Override
    public User signup(User user, MultipartFile file) throws IOException {
        User existingUser = findUserByUsername(user.getUserName());

        return null;
    }

    @Override
    public User getProfile(Long id) {
        User user = getUserById(id);
//        Picture pic = user.getProfilePicture();
//        if (pic != null && pic.isCompressed()) {
//            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
//            tempPicture.setCompressed(false);
//            user.setProfilePicture(tempPicture);
//        }
        return user;
    }

    @Override
    public User getPersonalProfile(Long id, Long otherId) {
        User user = getUserById(otherId);
//        Picture pic = user.getProfilePicture();
//        if (pic != null && pic.isCompressed()) {
//            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
//            pic.setCompressed(false);
//            user.setProfilePicture(tempPicture);
//        }
        return user;
    }

    @Override
    public void informPersonalProfile(Long id, SkillsAndExperience skill) {
        User user = getUserById(id);
        if (skill.getSkillType() == SkillType.EXPERIENCE) {
            skill.setUser(user);
        } else if (skill.getSkillType() == SkillType.SKILL) {
            skill.setUser(user);
        } else if (skill.getSkillType() == SkillType.EDUCATION) {
            skill.setUser(user);
        }
        skillsAndExperienceDao.save(skill);
    }

    @Override
    public void editUserJob(Long id, User user) {
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
    }

    @Override
    public User showProfile(Long id, Long otherUserId) {
        getUserById(id);
        User userPreview = getUserById(otherUserId);
//        Picture pic = userPreview.getProfilePicture();
//        if (pic != null && pic.isCompressed()) {
//            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), PictureSave.decompressBytes(pic.getBytes()));
//            userPreview.setProfilePicture(tempPicture);
//            pic.setCompressed(false);
//        }
        return userPreview;
    }

    @Override
    public User saveUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User findUserByUsername(String userName) {
        return userDao.findUserByUserName(userName);
    }

    @Override
    public UserResponse getUserByUsername(String userName) {
        User user = userRepository.getUserByUsername(userName);
        if (user == null){
            throw new UserNotFoundException(ObjectConstants.USER, userName);
        }
        return getUserResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User existingUser = findUserByUsername(createUserRequest.getUserName());
        User user = new User();
        if (existingUser == null) {
            user.setUserName(createUserRequest.getUserName());
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
            user.setPhoneNumber(createUserRequest.getPhoneNumber());
            user.setPassword(encoder.encode(createUserRequest.getPassword()));
            user = userRepository.createUser(user);
        } else {
            throw new ResourceFoundException(ObjectConstants.USER, UserConstants.USER_EMAIL, createUserRequest.getUserName());
        }
        return getUserResponse(user);
    }

    @Override
    public List<UserResponse> listAllUsers() {
        List<User> users = userRepository.listAllUsers();
        return getResponseList(users);
    }

    private List<UserResponse> getResponseList(List<User> users){
        return users.stream()
                .filter(entity -> entity.getRole().getName() != RoleType.ADMIN)
                .map(entity -> ResponseConverter.copyProperties(entity, UserResponse.class))
                .collect(Collectors.toList());
    }

    private UserResponse getUserResponse(User user) {
        return ResponseConverter.copyProperties(user, UserResponse.class);
    }
}

