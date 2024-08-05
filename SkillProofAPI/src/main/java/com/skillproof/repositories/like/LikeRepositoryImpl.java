package com.skillproof.repositories.like;

import com.skillproof.model.entity.Like;
import com.skillproof.repositories.LikeDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    private final LikeDao likeDao;

    public LikeRepositoryImpl(LikeDao likeDao) {
        this.likeDao = likeDao;
    }


    @Override
    public Like createLike(Like like) {
        return likeDao.saveAndFlush(like);
    }

    @Override
    public Like getLikeById(Long id) {
        return likeDao.findById(id).orElse(null);
    }

    @Override
    public List<Like> listAllLikes() {
        return likeDao.findAll();
    }

    @Override
    public void deleteLike(Long id) {
        likeDao.deleteById(id);
    }

    @Override
    public List<Like> listLikesByPostId(Long postId) {
        return likeDao.findLikeByPostId(postId);
    }
}
