package com.linkedin.linkedinclone.services.user;

import com.linkedin.linkedinclone.dto.NewUserInfo;
import com.linkedin.linkedinclone.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {

    void newConnection(User user, Long userFollowingId);

    Set<Post> getFeedPosts(User user);

    void newPostInterested(User user, Post post);

    void newPostComment(User user, Post post, Comment comment);

    Set<User> getUserNetwork(User currentUser);

    List<User> getAllUsers();

    List<User> getUsersWithOutAdmin();

    Integer hasApplied(User u, Job j);

    boolean hasLiked(User u, Post p);

    Integer numOfComments(User u, Post p);

    Integer matchingSkills(User u, Job j);

    Integer matchingSkills(User u, Post p);

    boolean isNetworkPost(User u, Post p);

    User getUserById(Long id);

    User signup(User user, MultipartFile file) throws IOException;

    User getProfile(Long id);

    User getPersonalProfile(Long id, Long otherId);

    void informPersonalProfile(Long id, SkillsAndExperience skill);

    void editUserJob(Long id, User user);

    User showProfile(Long id, Long otherUserId);

    User saveUser(User user);

    User findUserByUsername(String userName);
}
