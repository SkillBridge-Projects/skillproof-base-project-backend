package com.skillproof.services.comment;

import com.skillproof.model.request.comment.CommentResponse;
import com.skillproof.model.request.comment.CreateCommentRequest;
import com.skillproof.model.request.comment.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CreateCommentRequest createCommentRequest);

    List<CommentResponse> listCommentsByPostId(Long postId);

    CommentResponse updateComment(Long id, UpdateCommentRequest updateCommentRequest);

    CommentResponse getCommentById(Long id);

    List<CommentResponse> listAllComments();

    void deleteCommentById(Long id);
}
