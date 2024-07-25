package com.skillproof.skillproofapi.services.feed;

import com.skillproof.skillproofapi.constants.ErrorMessageConstants;
import com.skillproof.skillproofapi.constants.ObjectConstants;
import com.skillproof.skillproofapi.exceptions.PostNotFoundException;
import com.skillproof.skillproofapi.exceptions.ResourceNotFoundException;
import com.skillproof.skillproofapi.recommendation.RecommendationAlgos;
import com.skillproof.skillproofapi.services.user.UserService;
import com.skillproof.skillproofapi.enumerations.NotificationType;
import com.skillproof.skillproofapi.model.entity.*;
import com.skillproof.skillproofapi.repositories.*;
import com.skillproof.skillproofapi.utils.PictureSave;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final UserService userService;
    private final UserDao userDao;
    private final PostRepository postRepository;
    private final PictureRepository pictureRepository;
    private final NotificationRepository notificationRepository;
    private final InterestReactionRepository interestReactionRepository;

    @Override
    public Set<Post> getFeedPosts(Long id) {
        //TODO: need to add log statements
        User user = userService.getUserById(id);
        Set<Post> feedPosts = new HashSet<>(user.getPosts());
        Set<Connection> connections = user.getUsersFollowing();
        for(Connection con: connections) {
            if(con.getIsAccepted()){
                User userFollowing = con.getUserFollowed();
                feedPosts.addAll(userFollowing.getPosts());

                Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

                for(InterestReaction ir: interestReactions){
                    feedPosts.add(ir.getPost());
                }
            }
        }

        connections = user.getUserFollowedBy();
        for(Connection con: connections) {
            if(con.getIsAccepted()){
                User userFollowing = con.getUserFollowing();
                feedPosts.addAll(userFollowing.getPosts());

                Set<InterestReaction> interestReactions = userFollowing.getInterestReactions();

                for(InterestReaction ir: interestReactions){
                    feedPosts.add(ir.getPost());
                }
            }
        }

        for (Post post : feedPosts) {
            User owner = post.getOwner();
            Picture pic = owner.getProfilePicture();
            if (pic != null) {
                if (pic.isCompressed()) {
                    Picture dbPic = pictureRepository.findById(pic.getId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.PICTURE, pic.getId())));
                    Picture tempPicture = new Picture(dbPic.getId(), dbPic.getName(), dbPic.getType(),
                            PictureSave.decompressBytes(dbPic.getBytes()));
                    tempPicture.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }
            Set<Comment> comments = post.getComments();
            for (Comment c : comments) {
                User commentOwner = c.getUserMadeBy();
                Picture profilePicture = commentOwner.getProfilePicture();
                if (profilePicture != null) {
                    if (profilePicture.isCompressed()) {
                        Picture tempPicture = new Picture(profilePicture.getId(), profilePicture.getName(),
                                profilePicture.getType(), PictureSave.decompressBytes(profilePicture.getBytes()));
                        tempPicture.setCompressed(false);
                        commentOwner.setProfilePicture(tempPicture);
                    }
                }
            }
            Set<Picture> newPics = new HashSet<>();
            for (Picture pict : post.getPictures()) {
                if (pict != null) {
                    if (pict.isCompressed()) {
                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), PictureSave.decompressBytes(pict.getBytes()));
                        tempPicture.setCompressed(false);
                        newPics.add(tempPicture);
                    } else
                        newPics.add(pict);
                }
            }
            post.setPictures(newPics);
        }
        return feedPosts;
    }

    private void setFeedPosts(Set<Connection> connections, Set<Post> feedPosts) {
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
    }

    @Override
    public void newPost(Long userId, Post post, MultipartFile[] files) throws IOException {
        User currentUser = userService.getUserById(userId);
        post.setOwner(currentUser);
        if (files != null) {
            for (MultipartFile file : files) {
                Picture pic = new Picture(file.getOriginalFilename(), file.getContentType(), PictureSave.compressBytes(file.getBytes()));
                pic.setCompressed(true);
                pic.setPost(post);
                pictureRepository.save(pic);
            }
        }
        postRepository.save(post);
    }

    @Override
    public void newInterestedPost(Long userId, Long postId) {
        User currentUser = userService.getUserById(userId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(
                String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.POST, postId)));

        // IF reaction exists delete it
        for (InterestReaction ir : post.getInterestReactions()) {
            if (ir.getUserMadeBy() == currentUser) {
                currentUser.getInterestReactions().remove(ir);
                currentUser.setInterestReactions(currentUser.getInterestReactions());
                post.getInterestReactions().remove(ir);
                post.setInterestReactions(post.getInterestReactions());
                ir.setUserMadeBy(null);
                ir.setPost(null);
                userDao.save(currentUser);
                postRepository.save(post);
                interestReactionRepository.delete(ir);
                return;
            }
        }
        InterestReaction newReaction = new InterestReaction(currentUser, post);
        User postOwner = post.getOwner();
        if (postOwner != currentUser) {
            Notification notification = new Notification(NotificationType.INTEREST, postOwner, newReaction);
            notificationRepository.save(notification);
        }
        interestReactionRepository.save(newReaction);
    }

    @Override
    public void newComment(Long userId, Long postId, Comment comment) {
        User currentUser = userService.getUserById(userId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(
                String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.POST, postId)));
        userService.newPostComment(currentUser, post, comment);
    }

    @Override
    public List<Post> getRecommendedPosts(Long userId) {
        RecommendationAlgos recAlgos = new RecommendationAlgos();
        List<Post> recommendedPosts = new ArrayList<>();
        recAlgos.recommendedPosts(userDao, postRepository, userService);
        User currentUser = userService.getUserById(userId);

        if (!currentUser.getRecommendedPosts().isEmpty()) {
            for (Post post : currentUser.getRecommendedPosts()) {
                for (Post fp : getFeedPosts(userId)) {
                    Long id1 = fp.getId();
                    Long id2 = post.getId();
                    if (id1 == id2)
                        recommendedPosts.add(post);
                }
            }
        } else {
            return new ArrayList<>(getFeedPosts(userId));
        }
        Collections.reverse(recommendedPosts);
        for (Post post : recommendedPosts) {
            User owner = post.getOwner();
            Picture pic = owner.getProfilePicture();
            if (pic != null) {
                if (pic.isCompressed()) {
                    Picture dbPic = pictureRepository.findById(pic.getId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.PICTURE, pic.getId())));
                    Picture tempPicture = new Picture(dbPic.getId(), dbPic.getName(), dbPic.getType(), PictureSave.decompressBytes(dbPic.getBytes()));
                    tempPicture.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }
            Set<Comment> comments = post.getComments();
            for (Comment c : comments) {
                User commentOwner = c.getUserMadeBy();
                Picture cpic = commentOwner.getProfilePicture();
                if (cpic != null) {
                    if (cpic.isCompressed()) {
                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(), PictureSave.decompressBytes(cpic.getBytes()));
                        tempPicture.setCompressed(false);
                        commentOwner.setProfilePicture(tempPicture);
                    }
                }
            }
            Set<Picture> newPics = new HashSet<>();
            for (Picture pict : post.getPictures()) {
                if (pict != null) {
                    if (pict.isCompressed()) {
                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), PictureSave.decompressBytes(pict.getBytes()));
                        tempPicture.setCompressed(false);
                        newPics.add(tempPicture);
                    } else
                        newPics.add(pict);
                }
            }
            post.setPictures(newPics);
        }
        return recommendedPosts;
    }

    @Override
    public InterestReaction isInterestedPost(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(
                String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.POST, postId)));
        User currentUser = userService.getUserById(userId);
        for (InterestReaction ir : post.getInterestReactions()) {
            if (ir.getUserMadeBy() == currentUser) {
                return ir;
            }
        }
        return null;
    }
}
