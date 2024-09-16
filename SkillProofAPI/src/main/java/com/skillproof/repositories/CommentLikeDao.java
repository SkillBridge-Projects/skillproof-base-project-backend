package com.skillproof.repositories;

import com.skillproof.model.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikeDao extends JpaRepository<CommentLike, Long> {
    List<CommentLike> findAllByCommentId(Long commentId);
}
