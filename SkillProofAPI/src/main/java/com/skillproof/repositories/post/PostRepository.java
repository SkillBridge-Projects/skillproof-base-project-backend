package com.skillproof.repositories.post;

import com.skillproof.model.entity.Portfolio;
import com.skillproof.model.entity.Post;

import java.util.List;

public interface PostRepository {
    Post createPost(Post post);

    Post getPostById(Long id);

    Post updatePost(Post post);

    List<Post> listAllPosts();

    void deletePost(Long id);

    List<Post> findByUserId(String userId);
}
