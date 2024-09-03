package com.skillproof.services.post;

import com.skillproof.model.request.portfolio.CreatePortfolioMediaRequest;
import com.skillproof.model.request.post.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostResponse createPost(String content, String userId, List<MultipartFile> image,
                            List<MultipartFile> video) throws Exception;

    PostResponse getPostById(Long id);

    PostResponse updatePost(Long id, String content, List<MultipartFile> images, List<MultipartFile> videos) throws Exception;

    List<PostResponse> listAllPosts();

    void deletePostById(Long id);

    Feed listAllFeed(List<String> userIds);

    PortfolioResponse addPortfolioVideo(String userId, List<CreatePortfolioMediaRequest> requests, MultipartFile video) throws Exception;

    PortfolioResponse getPortfolioByUserId(String userId);

    PortfolioResponse updatePortfolio(Long id, MultipartFile video) throws Exception;
}

