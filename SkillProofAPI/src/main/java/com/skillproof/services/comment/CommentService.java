package com.skillproof.services.comment;

import com.skillproof.model.request.comment.*;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CreateCommentRequest createCommentRequest);

    List<CommentResponse> listCommentsByPostId(Long postId);

    CommentResponse updateComment(Long id, UpdateCommentRequest updateCommentRequest);

    CommentResponse getCommentById(Long id);

    List<CommentResponse> listAllComments();

    void deleteCommentById(Long id);

    CommentLikeResponse likeComment(CreateCommentLikeRequest request);

    CommentReplyResponse replyToComment(CreateCommentReplyRequest request);

    List<CommentLikeResponse> listAllLikesForComments(Long commentId);

    List<CommentReplyResponse> listAllRepliesForComments(Long commentId);
}
