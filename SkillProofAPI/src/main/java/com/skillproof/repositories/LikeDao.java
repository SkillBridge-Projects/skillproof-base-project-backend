package com.skillproof.repositories;

import com.skillproof.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeDao extends JpaRepository<Like, Long> {
    List<Like> findLikeByPostId(Long postId);
}

