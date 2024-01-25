package com.linkedin.linkedinclone.services.user;

import com.linkedin.linkedinclone.constants.ErrorMessageConstants;
import com.linkedin.linkedinclone.constants.ObjectConstants;
import com.linkedin.linkedinclone.enumerations.SkillType;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.repositories.*;
import com.linkedin.linkedinclone.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.linkedin.linkedinclone.enumerations.NotificationType.*;
import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SkillsAndExperienceRepository skillsAndExperienceRepository;
    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;
    private final ConnectionRepository connectionRepository;
    private final InterestReactionRepository interestReactionRepository;


    /* --------- NETWORK --------- */

    public void newConnection(User user, Long userFollowingId) {
        User userToBeFollowed = getUserById(userFollowingId);
        Connection newConnection = new Connection(user, userToBeFollowed);
        Notification notification = new Notification(CONNECTION_REQUEST, userToBeFollowed, newConnection);
        notificationRepository.save(notification);
        connectionRepository.save(newConnection);
    }

    /* --------- FEED --------- */

    public Set<Post> getFeedPosts(User user) {
        Set<Post> feedPosts = new HashSet<>(user.getPosts());

        Set<Connection> connections = user.getUsersFollowing();
        for (Connection con : connections) {
            if (con.getIsAccepted()) {
                User userFollowing = con.getUserFollowed();
                feedPosts.addAll(userFollowing.getPosts());
                Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();
                for (InterestReaction ir : interestReactions) {
                    feedPosts.add(ir.getPost());
                }
            }
        }
        connections = user.getUserFollowedBy();
        for (Connection con : connections) {
            if (con.getIsAccepted()) {
                User userFollowing = con.getUserFollowing();
                feedPosts.addAll(userFollowing.getPosts());
                Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();
                for (InterestReaction ir : interestReactions) {
                    feedPosts.add(ir.getPost());
                }
            }
        }

        for (Post p : feedPosts) {
            User owner = p.getOwner();

            Picture pic = owner.getProfilePicture();
            if (pic != null) {
                if (pic.isCompressed()) {
                    Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), decompressBytes(pic.getBytes()));
                    pic.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }

            Set<Comment> comments = p.getComments();
            for (Comment c : comments) {
                User commentOwner = c.getUserMadeBy();
                Picture cpic = commentOwner.getProfilePicture();
                if (cpic != null) {
                    if (cpic.isCompressed()) {
                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(), decompressBytes(cpic.getBytes()));
                        cpic.setCompressed(false);
                        commentOwner.setProfilePicture(tempPicture);
                    }
                }
            }

            Set<Picture> newPicts = new HashSet<>();
            for (Picture pict : p.getPictures()) {
                if (pict != null) {
                    if (pict.isCompressed()) {
                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), decompressBytes(pict.getBytes()));
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
        User postOwner = post.getOwner();
        if (postOwner != user) {
            Notification notification = new Notification(INTEREST, postOwner, newReaction);
            notificationRepository.save(notification);
        }
        interestReactionRepository.save(newReaction);
    }

    public void newPostComment(User user, Post post, Comment comment) {
        comment.setUserMadeBy(user);
        comment.setPost(post);
        User postOwner = post.getOwner();
        if (postOwner != user) {
            Notification notification = new Notification(COMMENT, postOwner, comment);
            notificationRepository.save(notification);
        }
        commentRepository.save(comment);
    }


    public Set<User> getUserNetwork(User currentUser) {
        Set<User> network = new HashSet<>();

        Set<Connection> connectionsFollowing = currentUser.getUsersFollowing();
        for (Connection con : connectionsFollowing) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUserFollowed();
                network.add(userinNetwork);
            }
        }

        Set<Connection> connectionsFollowedBy = currentUser.getUserFollowedBy();
        for (Connection con : connectionsFollowedBy) {
            if (con.getIsAccepted()) {
                User userinNetwork = con.getUserFollowing();
                network.add(userinNetwork);
            }
        }

        for (User user : network) {
            Picture uPic = user.getProfilePicture();
            if (uPic != null && uPic.isCompressed()) {
                Picture temp = new Picture(uPic.getName(), uPic.getType(), decompressBytes(uPic.getBytes()));
                user.setProfilePicture(temp);
            }
        }
        return network;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersWithOutAdmin() {
        Set<User> usersWithoutAdmin = new HashSet<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (!user.getName().equals("admin")) {
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
        Set<SkillsAndExperience> skills = u.getSkills();
        Integer avgDistance = 0;
        for (SkillsAndExperience s : skills) {
            Integer editDist = Utils.minDistance(s.getDescription().toLowerCase(), j.getTitle().toLowerCase());
            avgDistance += editDist;
        }
        if (u.getCurrentJob() != null)
            avgDistance += Utils.minDistance(u.getCurrentJob().toLowerCase(), j.getTitle().toLowerCase());
        if (avgDistance != 0) {
            return (int) (((double) avgDistance) / ((double) skills.size()));
        } else {
            return -1;
        }
    }

    public Integer matchingSkills(User u, Post p) {
        Set<SkillsAndExperience> skills = u.getSkills();
        Integer avgDistance = 0;
        for (SkillsAndExperience s : skills) {
            Integer editDist = Utils.minDistance(s.getDescription().toLowerCase(), p.getContent().toLowerCase());
            avgDistance += editDist;
        }

        if (u.getCurrentJob() != null)
            avgDistance += Utils.minDistance(u.getCurrentJob().toLowerCase(), p.getContent().toLowerCase());

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
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.USER, id)));
    }

    @Override
    public User signup(User user, MultipartFile file) throws IOException {
        User existingUser = findUserByUsername(user.getUsername());

        return null;
    }

    @Override
    public User getProfile(Long id) {
        User user = getUserById(id);
        Picture pic = user.getProfilePicture();
        if (pic != null && pic.isCompressed()) {
            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), decompressBytes(pic.getBytes()));
            tempPicture.setCompressed(false);
            user.setProfilePicture(tempPicture);
        }
        return user;
    }

    @Override
    public User getPersonalProfile(Long id, Long otherId) {
        User user = getUserById(otherId);
        Picture pic = user.getProfilePicture();
        if (pic != null && pic.isCompressed()) {
            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), decompressBytes(pic.getBytes()));
            pic.setCompressed(false);
            user.setProfilePicture(tempPicture);
        }
        return user;
    }

    @Override
    public void informPersonalProfile(Long id, SkillsAndExperience skill) {
        User user = getUserById(id);
        if (skill.getType() == SkillType.EXPERIENCE) {
            skill.setUserExp(user);
        } else if (skill.getType() == SkillType.SKILL) {
            skill.setUserSk(user);
        } else if (skill.getType() == SkillType.EDUCATION) {
            skill.setUserEdu(user);
        }
        skillsAndExperienceRepository.save(skill);
    }

    @Override
    public void editUserJob(Long id, User user) {
        User newUser = getUserById(id);
        if (user.getCurrentJob() != null)
            newUser.setCurrentJob(user.getCurrentJob());
        if (user.getCurrentCompany() != null)
            newUser.setCurrentCompany(user.getCurrentCompany());
        if (user.getCity() != null)
            newUser.setCity(user.getCity());
        if (user.getWebsite() != null)
            newUser.setWebsite(user.getWebsite());
        if (user.getGithub() != null)
            newUser.setGithub(user.getGithub());
        if (user.getTwitter() != null)
            newUser.setTwitter(user.getTwitter());
        userRepository.save(newUser);
    }

    @Override
    public User showProfile(Long id, Long otherUserId) {
        getUserById(id);
        User userPreview = getUserById(otherUserId);
        Picture pic = userPreview.getProfilePicture();
        if (pic != null && pic.isCompressed()) {
            Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(), decompressBytes(pic.getBytes()));
            userPreview.setProfilePicture(tempPicture);
            pic.setCompressed(false);
        }
        return userPreview;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String userName) {
        return userRepository.findUserByUsername(userName);
    }
}

