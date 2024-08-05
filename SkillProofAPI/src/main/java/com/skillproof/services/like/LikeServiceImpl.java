package com.skillproof.services.like;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Like;
import com.skillproof.model.entity.Post;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.like.CreateLikeRequest;
import com.skillproof.model.request.like.LikeResponse;
import com.skillproof.repositories.like.LikeRepository;
import com.skillproof.repositories.post.PostRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    private static final Logger LOG = LoggerFactory.getLogger(LikeServiceImpl.class);

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikeRepository likeRepository, UserRepository userRepository,
                           PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @Override
    public LikeResponse createLike(CreateLikeRequest createLikeRequest) {
        LOG.debug("Start of createLike method - LikeServiceImpl");
        User user = userRepository.getUserById(createLikeRequest.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", createLikeRequest.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, createLikeRequest.getUserId());
        }

        Post post = postRepository.getPostById(createLikeRequest.getPostId());
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", createLikeRequest.getPostId());
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, createLikeRequest.getPostId());
        }

        Like like = createLikeEntity(post, user);
        like = likeRepository.createLike(like);
        LOG.debug("End of createLike method - LikeServiceImpl");
        return getResponse(like);
    }

    @Override
    public List<LikeResponse> listLikesByPostId(Long postId) {
        LOG.debug("Start of getLikeByUserId method - LikeServiceImpl");
        Post post = postRepository.getPostById(postId);
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", postId);
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, postId);
        }
        List<Like> likes = likeRepository.listLikesByPostId(postId);
        LOG.debug("End of getLikeByUserId method - LikeServiceImpl");
        return getResponseList(likes);
    }

    @Override
    public LikeResponse getLikeById(Long id) {
        LOG.debug("Start of getLikeById method - LikeServiceImpl");
        Like like = likeRepository.getLikeById(id);
        if (like == null) {
            LOG.error("Like with {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.LIKE, ObjectConstants.ID, id);
        }
        return getResponse(like);
    }

    @Override
    public List<LikeResponse> listAllLikes() {
        LOG.debug("Start of listAllLikeDetails method - LikeServiceImpl");
        List<Like> likeResponses = likeRepository.listAllLikes();
        return getResponseList(likeResponses);
    }

    @Override
    public void deleteLikeById(Long id) {
        LOG.debug("Start of deleteLikeById method - LikeServiceImpl");
        Like like = likeRepository.getLikeById(id);
        if (ObjectUtils.isEmpty(like)) {
            LOG.error("Like with {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.LIKE, ObjectConstants.ID, id);
        }
        likeRepository.deleteLike(id);
    }

    private Like createLikeEntity(Post post, User user) {
        LOG.debug("Start of createLikeEntity method - LikeServiceImpl");
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        LOG.debug("End of createLikeEntity method - LikeServiceImpl");
        return like;
    }

    private LikeResponse getResponse(Like like) {
        LikeResponse likeResponse = ResponseConverter
                .copyProperties(like, LikeResponse.class);
        likeResponse.setUserId(like.getUser().getId());
        likeResponse.setPostId(like.getPost().getId());
        return likeResponse;
    }

    private List<LikeResponse> getResponseList(List<Like> likes) {
        return likes.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }
}
