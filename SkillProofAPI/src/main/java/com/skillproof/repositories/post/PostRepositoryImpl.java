package com.skillproof.repositories.post;

import com.skillproof.model.entity.Post;
import com.skillproof.repositories.PostDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final PostDao postDao;

    public PostRepositoryImpl(PostDao postDao) {
        this.postDao = postDao;
    }


    @Override
    public Post createPost(Post post) {
        return postDao.saveAndFlush(post);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return postDao.findById(postId);
    }

    @Override
    public Post sharePostToUser(Post sharedPost) {
        return postDao.save(sharedPost);
    }

    @Override
    public void deletePost(Long postId) {
        postDao.deleteById(postId);
    }
}