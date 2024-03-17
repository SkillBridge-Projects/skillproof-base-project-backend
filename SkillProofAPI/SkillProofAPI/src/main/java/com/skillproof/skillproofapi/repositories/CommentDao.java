package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long> {
}
