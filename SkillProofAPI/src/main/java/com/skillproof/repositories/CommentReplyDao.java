package com.skillproof.repositories;

import com.skillproof.model.entity.CommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReplyDao extends JpaRepository<CommentReply, Long> {
    List<CommentReply> findAllByCommentId(Long commentId);
}
