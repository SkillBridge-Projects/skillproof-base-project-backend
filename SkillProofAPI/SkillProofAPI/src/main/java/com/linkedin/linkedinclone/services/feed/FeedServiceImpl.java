package com.linkedin.linkedinclone.services.feed;

import com.linkedin.linkedinclone.constants.ErrorMessageConstants;
import com.linkedin.linkedinclone.constants.ObjectConstants;
import com.linkedin.linkedinclone.exceptions.PostNotFoundException;
import com.linkedin.linkedinclone.exceptions.ResourceNotFoundException;
import com.linkedin.linkedinclone.model.*;
import com.linkedin.linkedinclone.recommendation.RecommendationAlgos;
import com.linkedin.linkedinclone.repositories.*;
import com.linkedin.linkedinclone.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.linkedin.linkedinclone.enumerations.NotificationType.INTEREST;
import static com.linkedin.linkedinclone.utils.PictureSave.compressBytes;
import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

@Service
@AllArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final UserService userService;
    private final UserRepository userRepository;
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
                            decompressBytes(dbPic.getBytes()));
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
                                profilePicture.getType(), decompressBytes(profilePicture.getBytes()));
                        tempPicture.setCompressed(false);
                        commentOwner.setProfilePicture(tempPicture);
                    }
                }
            }
            Set<Picture> newPics = new HashSet<>();
            for (Picture pict : post.getPictures()) {
                if (pict != null) {
                    if (pict.isCompressed()) {
                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), decompressBytes(pict.getBytes()));
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
                Picture pic = new Picture(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
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
                userRepository.save(currentUser);
                postRepository.save(post);
                interestReactionRepository.delete(ir);
                return;
            }
        }
        InterestReaction newReaction = new InterestReaction(currentUser, post);
        User postOwner = post.getOwner();
        if (postOwner != currentUser) {
            Notification notification = new Notification(INTEREST, postOwner, newReaction);
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
        recAlgos.recommendedPosts(userRepository, postRepository, userService);
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
                    Picture tempPicture = new Picture(dbPic.getId(), dbPic.getName(), dbPic.getType(), decompressBytes(dbPic.getBytes()));
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
                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(), decompressBytes(cpic.getBytes()));
                        tempPicture.setCompressed(false);
                        commentOwner.setProfilePicture(tempPicture);
                    }
                }
            }
            Set<Picture> newPics = new HashSet<>();
            for (Picture pict : post.getPictures()) {
                if (pict != null) {
                    if (pict.isCompressed()) {
                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), decompressBytes(pict.getBytes()));
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
