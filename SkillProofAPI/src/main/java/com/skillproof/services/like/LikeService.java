package com.skillproof.services.like;

import com.skillproof.model.request.like.CreateLikeRequest;
import com.skillproof.model.request.like.LikeResponse;

import java.util.List;

public interface LikeService {
    LikeResponse createLike(CreateLikeRequest createLikeRequest);

    List<LikeResponse> listLikesByPostId(Long postId);

    LikeResponse getLikeById(Long id);

    List<LikeResponse> listAllLikes();

    void deleteLikeById(Long id);
}
