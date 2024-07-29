package com.skillproof.services.post;

import com.skillproof.model.request.post.CreatePostRequest;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.model.request.post.UpdatePostRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Override
    public PostResponse createPost(CreatePostRequest createPostRequest) {
        return null;
    }

    @Override
    public PostResponse getPostById(Long id) {
        return null;
    }

    @Override
    public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }
}
