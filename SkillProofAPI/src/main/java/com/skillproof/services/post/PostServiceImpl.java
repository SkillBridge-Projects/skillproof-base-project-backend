package com.skillproof.services.post;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Post;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.repositories.post.PostRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.AWSS3Service;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostServiceImpl.class);

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AWSS3Service awss3Service;


    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository, AWSS3Service awss3Service) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.awss3Service = awss3Service;
    }

    @Override
    public PostResponse createPost(String content, String userId, MultipartFile image, MultipartFile video) throws Exception {
        LOG.debug("Start of createPost method.");
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }

        String imageUrl = null;
        if (ObjectUtils.isNotEmpty(image)) {
            LOG.debug("Uploading image - Post");
            imageUrl = awss3Service.uploadFile(image);
        }

        String videoUrl = null;
        if (ObjectUtils.isNotEmpty(video)) {
            LOG.debug("Uploading video - Post");
            videoUrl = awss3Service.uploadFile(video);
        }

        Post post = createPostEntity(content, imageUrl, videoUrl, user);
        post = postRepository.createPost(post);
        LOG.debug("End of createPost method.");
        return getPostResponse(post);
    }

    private Post createPostEntity(String content, String imageUrl, String videoUrl, User user) {
        LOG.debug("Start of createPost method.");
        Post post = new Post();
        post.setContent(content);
        post.setImageUrl(imageUrl);
        post.setVideoUrl(videoUrl);
        post.setUser(user);
        return post;
    }

    @Override
    public PostResponse getPostById(Long id) {
        LOG.debug("Start of getPostById method.");
        Post post = postRepository.getPostById(id);
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, id);
        }
        return getPostResponse(post);
    }

    @Override
    public PostResponse updatePost(Long id, String content, MultipartFile image, MultipartFile video) throws Exception {
        LOG.debug("Start of updatePost method.");
        Post post = postRepository.getPostById(id);
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, id);
        }

        String oldPostedImageUrl = post.getImageUrl();
        // Delete the old posted image from S3
        if (StringUtils.isNotEmpty(oldPostedImageUrl)) {
            LOG.debug("Deleting old posted image");
            awss3Service.deleteFile(oldPostedImageUrl);
        }

        String oldPostedVideoUrl = post.getVideoUrl();
        // Delete the old posted video from S3
        if (StringUtils.isNotEmpty(oldPostedVideoUrl)) {
            LOG.debug("Deleting old posted video");
            awss3Service.deleteFile(oldPostedVideoUrl);
        }

        String newImageUrl = null;
        if (ObjectUtils.isNotEmpty(image)) {
            LOG.debug("Uploading image - Post");
            newImageUrl = awss3Service.uploadFile(image);
        }

        String newVideoUrl = null;
        if (ObjectUtils.isNotEmpty(video)) {
            LOG.debug("Uploading video - Post");
            newVideoUrl = awss3Service.uploadFile(video);
        }

        preparePostEntity(content, newImageUrl, newVideoUrl, post);
        post = postRepository.updatePost(post);
        LOG.debug("End of updatePost method");
        return getPostResponse(post);
    }

    private void preparePostEntity(String content, String newImageUrl, String newVideoUrl, Post post) {
        LOG.debug("Start of preparePostEntity method");
        if (StringUtils.isNotEmpty(content)){
            post.setContent(content);
        }
        if (StringUtils.isNotEmpty(newImageUrl)){
            post.setImageUrl(newImageUrl);
        }
        if (StringUtils.isNotEmpty(newVideoUrl)){
            post.setVideoUrl(newVideoUrl);
        }
        LOG.debug("End of preparePostEntity method");
    }

    @Override
    public List<PostResponse> listAllPosts() {
        LOG.debug("Start of listAllPosts method.");
        List<Post> posts = postRepository.listAllPosts();
        return getPostResponseList(posts);
    }

    @Override
    public void deletePostById(Long id) {
        LOG.debug("Start of deletePostById method.");
        Post post = postRepository.getPostById(id);
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, id);
        }
        postRepository.deletePost(id);
    }

    private PostResponse getPostResponse(Post post) {
        PostResponse postResponse = ResponseConverter.copyProperties(post, PostResponse.class);
        postResponse.setImageUrl(awss3Service.getPresignedUrlForProfile(post.getImageUrl()));
        postResponse.setVideoUrl(awss3Service.getPresignedUrlForProfile(post.getVideoUrl()));
        postResponse.setUserId(post.getUser().getId());
        postResponse.setUserEmail(post.getUser().getEmailAddress());
        return postResponse;
    }

    private List<PostResponse> getPostResponseList(List<Post> posts) {
        return posts.stream()
                .map(this::getPostResponse)
                .collect(Collectors.toList());
    }
}
