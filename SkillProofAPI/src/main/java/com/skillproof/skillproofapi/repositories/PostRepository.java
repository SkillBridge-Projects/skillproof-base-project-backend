package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
