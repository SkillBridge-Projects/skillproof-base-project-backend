//package com.skillproof.skillproofapi.services.feed;
//
//import com.skillproof.skillproofapi.constants.ErrorMessageConstants;
//import com.skillproof.skillproofapi.constants.ObjectConstants;
//import com.skillproof.skillproofapi.exceptions.PostNotFoundException;
//import com.skillproof.skillproofapi.recommendation.RecommendationAlgos;
//import com.skillproof.skillproofapi.services.user.UserService;
//import com.skillproof.skillproofapi.enumerations.NotificationType;
//import com.skillproof.skillproofapi.model.entity.*;
//import com.skillproof.skillproofapi.repositories.*;
//import com.skillproof.skillproofapi.utils.PictureSave;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.*;
//
//@Service
//@AllArgsConstructor
//public class FeedServiceImpl implements FeedService {
//
//    private final UserService userService;
//    private final UserDao userDao;
//    private final PostDao postDao;
//    private final PictureDao pictureDao;
//    private final NotificationDao notificationDao;
//    private final InterestReactionDao interestReactionDao;
//
//    @Override
//    public Set<Post> getFeedPosts(Long id) {
//        //TODO: need to add log statements
//        User user = userService.getUserById(id);
//        Set<Post> feedPosts = new HashSet<>();
//        Collection<Connection> connections = new ArrayList<>();
//        for(Connection con: connections) {
//            if(con.getIsAccepted()){
////                User userFollowing = con.getUser();
//                feedPosts.addAll(new ArrayList<>());
//
//                Collection<InterestReaction> interestReactions = new ArrayList<>();
//
//                for(InterestReaction ir: interestReactions){
//                    feedPosts.add(ir.getPost());
//                }
//            }
//        }
//
//        connections = new ArrayList<>();
//        for(Connection con: connections) {
//            if(con.getIsAccepted()){
////                User userFollowing = con.getUser();
//                feedPosts.addAll(new ArrayList<>());
//
//                Collection<InterestReaction> interestReactions = new ArrayList<>();
//
//                for(InterestReaction ir: interestReactions){
//                    feedPosts.add(ir.getPost());
//                }
//            }
//        }
//
//        for (Post post : feedPosts) {
////            User owner = post.getUser();
////            Picture pic = owner.getProfilePicture();
////            if (pic != null) {
////                if (pic.isCompressed()) {
////                    Picture dbPic = pictureDao.findById(pic.getId())
////                            .orElseThrow(() -> new ResourceNotFoundException(
////                                    String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.PICTURE, pic.getId())));
////                    Picture tempPicture = new Picture(dbPic.getId(), dbPic.getName(), dbPic.getType(),
////                            PictureSave.decompressBytes(dbPic.getBytes()));
////                    tempPicture.setCompressed(false);
////                    owner.setProfilePicture(tempPicture);
////                }
////            }
//            Collection<Comment> comments = post.getComments();
////            for (Comment c : comments) {
////                User commentOwner = c.getUserMadeBy();
////                Picture profilePicture = commentOwner.getProfilePicture();
////                if (profilePicture != null) {
////                    if (profilePicture.isCompressed()) {
////                        Picture tempPicture = new Picture(profilePicture.getId(), profilePicture.getName(),
////                                profilePicture.getType(), PictureSave.decompressBytes(profilePicture.getBytes()));
////                        tempPicture.setCompressed(false);
////                        commentOwner.setProfilePicture(tempPicture);
////                    }
////                }
////            }
//            Set<Picture> newPics = new HashSet<>();
//            for (Picture pict : post.getPictures()) {
//                if (pict != null) {
//                    if (pict.isCompressed()) {
//                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), PictureSave.decompressBytes(pict.getBytes()));
//                        tempPicture.setCompressed(false);
//                        newPics.add(tempPicture);
//                    } else
//                        newPics.add(pict);
//                }
//            }
//            post.setPictures(newPics);
//        }
//        return feedPosts;
//    }
//
//    private void setFeedPosts(Set<Connection> connections, Set<Post> feedPosts) {
//        for (Connection con : connections) {
//            if (con.getIsAccepted()) {
////                User userFollowing = con.getUser();
//                feedPosts.addAll(new ArrayList<>());
//                Collection<InterestReaction> interestReactions = new ArrayList<>();
//                for (InterestReaction ir : interestReactions) {
//                    feedPosts.add(ir.getPost());
//                }
//            }
//        }
//    }
//
//    @Override
//    public void newPost(Long userId, Post post, MultipartFile[] files) throws IOException {
//        User currentUser = userService.getUserById(userId);
////        post.setUser(currentUser);
//        if (files != null) {
//            for (MultipartFile file : files) {
//                Picture pic = new Picture(file.getOriginalFilename(), file.getContentType(), PictureSave.compressBytes(file.getBytes()));
//                pic.setCompressed(true);
//                pic.setPost(post);
//                pictureDao.save(pic);
//            }
//        }
//        postDao.save(post);
//    }
//
//    @Override
//    public void newInterestedPost(Long userId, Long postId) {
//        User currentUser = userService.getUserById(userId);
//        Post post = postDao.findById(postId).orElseThrow(() -> new PostNotFoundException(
//                String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.POST, postId)));
//
//        // IF reaction exists delete it
//        for (InterestReaction ir : post.getInterestReactions()) {
////            if (ir.getUserMadeBy() == currentUser) {
////                post.getInterestReactions().remove(ir);
////                post.setInterestReactions(post.getInterestReactions());
////                ir.setUserMadeBy(null);
////                ir.setPost(null);
////                userDao.save(currentUser);
////                postDao.save(post);
////                interestReactionDao.delete(ir);
////                return;
////            }
//        }
//        InterestReaction newReaction = new InterestReaction(currentUser, post);
//        User postOwner = new User();
//        if (postOwner != currentUser) {
//            Notification notification = new Notification(NotificationType.INTEREST, postOwner, newReaction);
//            notificationDao.save(notification);
//        }
//        interestReactionDao.save(newReaction);
//    }
//
//    @Override
//    public void newComment(Long userId, Long postId, Comment comment) {
//        User currentUser = userService.getUserById(userId);
//        Post post = postDao.findById(postId).orElseThrow(() -> new PostNotFoundException(
//                String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.POST, postId)));
//        userService.newPostComment(currentUser, post, comment);
//    }
//
//    @Override
//    public List<Post> getRecommendedPosts(Long userId) {
//        RecommendationAlgos recAlgos = new RecommendationAlgos();
//        List<Post> recommendedPosts = new ArrayList<>();
//        recAlgos.recommendedPosts(userDao, postDao, userService);
//        User currentUser = userService.getUserById(userId);
//
//        if (true) {
////            for (Post post : currentUser) {
////                for (Post fp : getFeedPosts(userId)) {
////                    Long id1 = fp.getId();
////                    Long id2 = post.getId();
////                    if (id1 == id2)
////                        recommendedPosts.add(post);
////                }
////            }
//        } else {
//            return new ArrayList<>(getFeedPosts(userId));
//        }
//        Collections.reverse(recommendedPosts);
//        for (Post post : recommendedPosts) {
////            User owner = post.getUser();
////            Picture pic = owner.getProfilePicture();
////            if (pic != null) {
////                if (pic.isCompressed()) {
////                    Picture dbPic = pictureDao.findById(pic.getId())
////                            .orElseThrow(() -> new ResourceNotFoundException(
////                                    String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.PICTURE, pic.getId())));
////                    Picture tempPicture = new Picture(dbPic.getId(), dbPic.getName(), dbPic.getType(), PictureSave.decompressBytes(dbPic.getBytes()));
////                    tempPicture.setCompressed(false);
////                    owner.setProfilePicture(tempPicture);
////                }
////            }
////            Collection<Comment> comments = post.getComments();
////            for (Comment c : comments) {
////                User commentOwner = c.getUserMadeBy();
////                Picture cpic = commentOwner.getProfilePicture();
////                if (cpic != null) {
////                    if (cpic.isCompressed()) {
////                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(), PictureSave.decompressBytes(cpic.getBytes()));
////                        tempPicture.setCompressed(false);
////                        commentOwner.setProfilePicture(tempPicture);
////                    }
////                }
////            }
//            Set<Picture> newPics = new HashSet<>();
//            for (Picture pict : post.getPictures()) {
//                if (pict != null) {
//                    if (pict.isCompressed()) {
//                        Picture tempPicture = new Picture(pict.getId(), pict.getName(), pict.getType(), PictureSave.decompressBytes(pict.getBytes()));
//                        tempPicture.setCompressed(false);
//                        newPics.add(tempPicture);
//                    } else
//                        newPics.add(pict);
//                }
//            }
//            post.setPictures(newPics);
//        }
//        return recommendedPosts;
//    }
//
//    @Override
//    public InterestReaction isInterestedPost(Long userId, Long postId) {
//        Post post = postDao.findById(postId).orElseThrow(() -> new PostNotFoundException(
//                String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.POST, postId)));
//        User currentUser = userService.getUserById(userId);
//        for (InterestReaction ir : post.getInterestReactions()) {
////            if (ir.getUserMadeBy() == currentUser) {
////                return ir;
////            }
//        }
//        return null;
//    }
//}