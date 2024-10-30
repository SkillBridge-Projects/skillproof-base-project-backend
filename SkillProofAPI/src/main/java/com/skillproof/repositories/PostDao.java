package com.skillproof.repositories;

import com.skillproof.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {

    List<Post> findByUserId(String userId);
}

