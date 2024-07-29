package com.skillproof.services.post;

import com.skillproof.model.request.post.CreatePostRequest;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.model.request.post.UpdatePostRequest;

public interface PostService {

    PostResponse createPost(CreatePostRequest createPostRequest);

    PostResponse getPostById(Long id);

    PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest);

    void deletePost(Long id);
}

