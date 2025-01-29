package com.skillproof.repositories.post;

import com.skillproof.model.entity.Post;

import java.util.Optional;

public interface PostRepository {

    Post createPost(Post post);

    Optional<Post> findById(Long postId);

    Post sharePostToUser(Post sharedPost);

    void deletePost(Long postId);
}
