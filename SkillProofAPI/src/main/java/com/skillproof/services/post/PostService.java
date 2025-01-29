package com.skillproof.services.post;

import com.skillproof.model.entity.Post;
import com.skillproof.model.request.post.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

public interface PostService {

    PostResponse createPost(String content, String userId, List<MultipartFile> images,
                            List<MultipartFile> videos) throws Exception;

    Post sharePostAndNotify(Long postId, String userId) throws MessagingException;

    void deletePostById(Long postId);
}
