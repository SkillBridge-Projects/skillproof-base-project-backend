package com.skillproof.repositories.comment;

import com.skillproof.model.entity.Comment;
import com.skillproof.model.entity.CommentLike;
import com.skillproof.model.entity.CommentReply;

import java.util.List;

public interface CommentRepository {

    Comment createComment(Comment comment);

    Comment getCommentById(Long id);

    Comment updateComment(Comment comment);

    List<Comment> listAllComments();

    void deleteComment(Long id);

    List<Comment> listCommentsByPostId(Long postId);

    CommentReply saveReplyToComment(CommentReply commentReply);

    CommentLike saveCommentLike(CommentLike commentLike);

    List<CommentLike> listAllLikesForComments(Long commentId);

    List<CommentReply> listAllRepliesForComments(Long commentId);
}
