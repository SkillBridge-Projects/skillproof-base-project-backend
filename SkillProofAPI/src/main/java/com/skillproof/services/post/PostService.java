package com.skillproof.services.post;

import com.skillproof.model.request.post.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponse createPost(String content, String userId, List<MultipartFile> image,
                            List<MultipartFile> video) throws Exception;


}

