package com.skillproof.services.post;

import com.skillproof.model.request.post.CreatePostRequest;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.model.request.post.UpdatePostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponse createPost(String content, String userId, MultipartFile image, MultipartFile video) throws Exception;

    PostResponse getPostById(Long id);

    PostResponse updatePost(Long id, String content, MultipartFile image, MultipartFile video) throws Exception;

    List<PostResponse> listAllPosts();

    void deletePostById(Long id);
}

