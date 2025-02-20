package com.skillproof.skillproofapi.services.feed;

import com.skillproof.skillproofapi.model.entity.Comment;
import com.skillproof.skillproofapi.model.entity.InterestReaction;
import com.skillproof.skillproofapi.model.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface FeedService {

    Set<Post> getFeedPosts(Long id);

    void newPost(Long id, Post post, MultipartFile[] files) throws IOException;

    void newInterestedPost(Long id, Long postId);

    void newComment(Long userId, Long postId, Comment comment);

    List<Post> getRecommendedPosts(Long userId);

    InterestReaction isInterestedPost(Long userId, Long postId);
}
