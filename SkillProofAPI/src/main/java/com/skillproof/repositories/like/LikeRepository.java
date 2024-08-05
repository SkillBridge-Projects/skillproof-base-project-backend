package com.skillproof.repositories.like;

import com.skillproof.model.entity.Like;

import java.util.List;

public interface LikeRepository {
    Like createLike(Like like);

    Like getLikeById(Long id);

    List<Like> listAllLikes();

    void deleteLike(Long id);

    List<Like> listLikesByPostId(Long postId);
}
