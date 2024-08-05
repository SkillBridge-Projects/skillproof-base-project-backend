package com.skillproof.repositories.comment;

import com.skillproof.model.entity.Comment;
import com.skillproof.repositories.CommentDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentDao commentDao;

    public CommentRepositoryImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
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
}
