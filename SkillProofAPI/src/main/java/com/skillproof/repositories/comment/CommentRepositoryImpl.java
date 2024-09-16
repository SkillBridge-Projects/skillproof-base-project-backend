package com.skillproof.repositories.comment;

import com.skillproof.model.entity.Comment;
import com.skillproof.model.entity.CommentLike;
import com.skillproof.model.entity.CommentReply;
import com.skillproof.repositories.CommentDao;
import com.skillproof.repositories.CommentLikeDao;
import com.skillproof.repositories.CommentReplyDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentDao commentDao;
    private final CommentLikeDao commentLikeDao;
    private final CommentReplyDao commentReplyDao;

    public CommentRepositoryImpl(CommentDao commentDao, CommentLikeDao commentLikeDao, CommentReplyDao commentReplyDao) {
        this.commentDao = commentDao;
        this.commentLikeDao = commentLikeDao;
        this.commentReplyDao = commentReplyDao;
    }


    @Override
    public Comment createComment(Comment comment) {
        return commentDao.saveAndFlush(comment);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentDao.findById(id).orElse(null);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentDao.saveAndFlush(comment);
    }

    @Override
    public List<Comment> listAllComments() {
        return commentDao.findAll();
    }

    @Override
    public void deleteComment(Long id) {
        commentDao.deleteById(id);
    }

    @Override
    public List<Comment> listCommentsByPostId(Long postId) {
        return commentDao.findCommentByPostId(postId);
    }

    @Override
    public CommentReply saveReplyToComment(CommentReply commentReply) {
        return commentReplyDao.saveAndFlush(commentReply);
    }

    @Override
    public CommentLike saveCommentLike(CommentLike commentLike) {
        return commentLikeDao.saveAndFlush(commentLike);
    }

    @Override
    public List<CommentLike> listAllLikesForComments(Long commentId) {
        return commentLikeDao.findAllByCommentId(commentId);
    }

    @Override
    public List<CommentReply> listAllRepliesForComments(Long commentId) {
        return commentReplyDao.findAllByCommentId(commentId);
    }
}
