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

}
