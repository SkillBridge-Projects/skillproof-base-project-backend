package com.skillproof.repositories.post;

import com.skillproof.model.entity.Post;
import com.skillproof.repositories.PostDao;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Post getPostById(Long id) {
        return postDao.findById(id).orElse(null);
    }

    @Override
    public Post updatePost(Post post) {
        return postDao.saveAndFlush(post);
    }

    @Override
    public List<Post> listAllPosts() {
        return postDao.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postDao.deleteById(id);
    }

    @Override
    public List<Post> findByUserId(String userId) {
        return postDao.findByUserId(userId);
    }
}
