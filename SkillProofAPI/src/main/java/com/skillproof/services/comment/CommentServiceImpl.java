package com.skillproof.services.comment;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.*;
import com.skillproof.model.request.comment.*;
import com.skillproof.repositories.comment.CommentRepository;
import com.skillproof.repositories.post.PostRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.AWSS3Service;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AWSS3Service awss3Service;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
                              PostRepository postRepository, AWSS3Service awss3Service) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.awss3Service = awss3Service;
    }


    @Override
    public CommentResponse createComment(CreateCommentRequest createCommentRequest) {
        LOG.debug("Start of createComment method - CommentServiceImpl");
        User user = userRepository.getUserById(createCommentRequest.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", createCommentRequest.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, createCommentRequest.getUserId());
        }

        Post post = postRepository.getPostById(createCommentRequest.getPostId());
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", createCommentRequest.getPostId());
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, createCommentRequest.getPostId());
        }

        Comment comment = createCommentEntity(createCommentRequest.getContent(), post, user);
        comment = commentRepository.createComment(comment);
        LOG.debug("End of createComment method - CommentServiceImpl");
        return getResponse(comment);
    }

    @Override
    public List<CommentResponse> listCommentsByPostId(Long postId) {
        LOG.debug("Start of getCommentByUserId method - CommentServiceImpl");
        Post post = postRepository.getPostById(postId);
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", postId);
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, postId);
        }
        List<Comment> comments = commentRepository.listCommentsByPostId(postId);
        LOG.debug("End of getCommentByUserId method - CommentServiceImpl");
        return getResponseList(comments);
    }

    @Override
    public CommentResponse updateComment(Long id, UpdateCommentRequest updateCommentRequest) {
        LOG.debug("Start of updateComment method - CommentServiceImpl");
        Comment comment = commentRepository.getCommentById(id);
        if (ObjectUtils.isEmpty(comment)) {
            LOG.error("Comment with {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.COMMENT, ObjectConstants.ID, id);
        }
        if (StringUtils.isNotEmpty(updateCommentRequest.getContent())){
            comment.setContent(updateCommentRequest.getContent());
        }
        Comment updatedComment = commentRepository.updateComment(comment);
        LOG.debug("End of updateComment method - CommentServiceImpl");
        return getResponse(updatedComment);
    }

    @Override
    public CommentResponse getCommentById(Long id) {
        LOG.debug("Start of getCommentById method - CommentServiceImpl");
        Comment comment = commentRepository.getCommentById(id);
        if (comment == null) {
            LOG.error("Comment with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.LIKE, ObjectConstants.ID, id);
        }
        return getResponse(comment);
    }

    @Override
    public List<CommentResponse> listAllComments() {
        LOG.debug("Start of listAllCommentDetails method - CommentServiceImpl");
        List<Comment> commentResponses = commentRepository.listAllComments();
        return getResponseList(commentResponses);
    }

    @Override
    public void deleteCommentById(Long id) {
        LOG.debug("Start of deleteCommentById method - CommentServiceImpl");
        Comment comment = commentRepository.getCommentById(id);
        if (ObjectUtils.isEmpty(comment)) {
            LOG.error("Comment with {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.LIKE, ObjectConstants.ID, id);
        }
        commentRepository.deleteComment(id);
    }

    @Override
    public CommentLikeResponse likeComment(CreateCommentLikeRequest request) {
        User user = userRepository.getUserById(request.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", request.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, request.getUserId());
        }
        Comment comment = commentRepository.getCommentById(request.getCommentId());
        if (ObjectUtils.isEmpty(comment)) {
            LOG.error("Comment with {} not found", request.getCommentId());
            throw new ResourceNotFoundException(ObjectConstants.LIKE, ObjectConstants.ID, request.getCommentId());
        }

        CommentLike commentLike = new CommentLike();
        commentLike.setUser(user);
        commentLike.setComment(comment);

        CommentLike savedLike = commentRepository.saveCommentLike(commentLike);

        return convertToCommentLikeResponse(savedLike);
    }

    @Override
    public CommentReplyResponse replyToComment(CreateCommentReplyRequest request) {
        User user = userRepository.getUserById(request.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", request.getUserId());
            throw new UserNotFoundException(ObjectConstants.USER, request.getUserId());
        }
        Comment comment = commentRepository.getCommentById(request.getCommentId());
        if (ObjectUtils.isEmpty(comment)) {
            LOG.error("Comment with {} not found", request.getCommentId());
            throw new ResourceNotFoundException(ObjectConstants.LIKE, ObjectConstants.ID, request.getCommentId());
        }

        CommentReply commentReply = new CommentReply();
        commentReply.setUser(user);
        commentReply.setComment(comment);
        commentReply.setContent(request.getContent());

        CommentReply savedReply = commentRepository.saveReplyToComment(commentReply);

        return convertToCommentReplyResponse(savedReply);
    }

    @Override
    public List<CommentLikeResponse> listAllLikesForComments(Long commentId) {
        List<CommentLike> likes = commentRepository.listAllLikesForComments(commentId);
        return likes.stream().map(this::convertToCommentLikeResponse).collect(Collectors.toList());
    }

    @Override
    public List<CommentReplyResponse> listAllRepliesForComments(Long commentId) {
        List<CommentReply> replies = commentRepository.listAllRepliesForComments(commentId);
        return replies.stream().map(this::convertToCommentReplyResponse).collect(Collectors.toList());
    }

    private CommentLikeResponse convertToCommentLikeResponse(CommentLike commentLike) {
        CommentLikeResponse response = ResponseConverter
                .copyProperties(commentLike, CommentLikeResponse.class);
        response.setId(commentLike.getId());
        response.setCommentId(commentLike.getComment().getId());
        response.setUserId(commentLike.getUser().getId());
        response.setUserName(commentLike.getUser().getFirstName() + " " + commentLike.getUser().getLastName());
        response.setProfilePicture(awss3Service.getPresignedUrl(commentLike.getUser().getProfilePicture()));
        return response;
    }

    private CommentReplyResponse convertToCommentReplyResponse(CommentReply commentReply) {
        CommentReplyResponse response = ResponseConverter
                .copyProperties(commentReply, CommentReplyResponse.class);
        response.setCommentId(commentReply.getComment().getId());
        response.setUserId(commentReply.getUser().getId());
        response.setUserName(commentReply.getUser().getFirstName() + " " + commentReply.getUser().getLastName());
        response.setProfilePicture(awss3Service.getPresignedUrl(commentReply.getUser().getProfilePicture()));
        return response;
    }

    private Comment createCommentEntity(String content, Post post, User user) {
        LOG.debug("Start of createCommentEntity method - CommentServiceImpl");
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        LOG.debug("End of createCommentEntity method - CommentServiceImpl");
        return comment;
    }

    private CommentResponse getResponse(Comment comment) {
        CommentResponse commentResponse = ResponseConverter.copyProperties(comment, CommentResponse.class);
        commentResponse.setUserId(comment.getUser().getId());
        commentResponse.setPostId(comment.getPost().getId());
        commentResponse.setUserName(comment.getUser().getUserName());
        commentResponse.setProfilePicture(awss3Service.getPresignedUrl(comment.getUser().getProfilePicture()));
        return commentResponse;
    }

    private List<CommentResponse> getResponseList(List<Comment> comments) {
        return comments.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }
}
