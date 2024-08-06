package com.skillproof.services.post;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.InvalidRequestException;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Post;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.repositories.post.PostRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.AWSS3Service;
import com.skillproof.services.notification.NotificationService;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostServiceImpl.class);

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AWSS3Service awss3Service;
    private final NotificationService notificationService;


    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository,
                           AWSS3Service awss3Service, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.awss3Service = awss3Service;
        this.notificationService = notificationService;
    }

    @Override
    public PostResponse createPost(String content, String userId, List<MultipartFile> images, List<MultipartFile> videos) throws Exception {
        LOG.debug("Start of createPost method.");
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }

        List<String> imageUrls = null;
        if (CollectionUtils.isNotEmpty(images)) {
            LOG.debug("Uploading image - Post");
            imageUrls = uploadFiles(images);
        }

        List<String> videoUrls = null;
        if (CollectionUtils.isNotEmpty(videos)) {
            LOG.debug("Uploading video - Post");
            videoUrls = uploadFiles(videos);
        }

        Post post = createPostEntity(content, imageUrls, videoUrls, user);
        post = postRepository.createPost(post);

        //TODO: Need to send notification i think when we tag other users

        LOG.debug("End of createPost method.");
        return getPostResponse(post);
    }

    private List<String> uploadFiles(List<MultipartFile> files) {
        return files.stream().map(file -> {
            try {
                return awss3Service.uploadFile(file);
            } catch (Exception e) {
                LOG.error("Failed to Update file, Message : {}", e.getMessage());
                throw new InvalidRequestException(e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    private Post createPostEntity(String content, List<String> imageUrls, List<String> videoUrls, User user) {
        LOG.debug("Start of createPost method.");
        Post post = new Post();
        post.setContent(content);
        String allImageUrls = StringUtils.join(imageUrls, ",");
        post.setImageUrl(allImageUrls);
        String allVideoUrls = StringUtils.join(videoUrls, ",");
        post.setVideoUrl(allVideoUrls);
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
    public PostResponse updatePost(Long id, String content, List<MultipartFile> images, List<MultipartFile> videos) throws Exception {
        LOG.debug("Start of updatePost method.");
        Post post = postRepository.getPostById(id);
        if (ObjectUtils.isEmpty(post)) {
            LOG.error("Post with id {} not found.", id);
            throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, id);
        }

        List<String> oldPostedImageUrls = Arrays.asList(post.getImageUrl().split(","));
        // Delete the old posted image from S3
        if (CollectionUtils.isNotEmpty(oldPostedImageUrls)) {
            LOG.debug("Deleting old posted image");
            deleteFiles(oldPostedImageUrls);
        }

        List<String> oldPostedVideoUrls = Arrays.asList(post.getVideoUrl().split(","));
        // Delete the old posted video from S3
        if (CollectionUtils.isNotEmpty(oldPostedVideoUrls)) {
            LOG.debug("Deleting old posted video");
            deleteFiles(oldPostedVideoUrls);
        }

        List<String> newImageUrls = null;
        if (CollectionUtils.isNotEmpty(images)) {
            LOG.debug("Uploading image - Post");
            newImageUrls = uploadFiles(images);
        }

        List<String> newVideoUrls = null;
        if (ObjectUtils.isNotEmpty(videos)) {
            LOG.debug("Uploading video - Post");
            newVideoUrls = uploadFiles(videos);
        }

        preparePostEntity(content, newImageUrls, newVideoUrls, post);
        post = postRepository.updatePost(post);


        LOG.debug("End of updatePost method");
        return getPostResponse(post);
    }

    private void deleteFiles(List<String> files) {
        files.forEach(awss3Service::deleteFile);
    }

    private void preparePostEntity(String content, List<String> newImageUrls, List<String> newVideoUrls, Post post) {
        LOG.debug("Start of preparePostEntity method");
        if (StringUtils.isNotEmpty(content)) {
            post.setContent(content);
        }
        if (CollectionUtils.isNotEmpty(newImageUrls)) {
            post.setImageUrl(StringUtils.join(",", newImageUrls));
        }
        if (CollectionUtils.isNotEmpty(newVideoUrls)) {
            post.setVideoUrl(StringUtils.join(",", newVideoUrls));
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
        postResponse.setImageUrls(getPreSignedUrls(post.getImageUrl()));
        postResponse.setVideoUrls(getPreSignedUrls(post.getVideoUrl()));
        postResponse.setUserId(post.getUser().getId());
        postResponse.setUserEmail(post.getUser().getEmailAddress());
        return postResponse;
    }

    private List<String> getPreSignedUrls(String baseUrl) {
        return Arrays.stream(baseUrl.split(","))
                .map(awss3Service::getPresignedUrl)
                .collect(Collectors.toList());
    }

    private List<PostResponse> getPostResponseList(List<Post> posts) {
        return posts.stream()
                .map(this::getPostResponse)
                .collect(Collectors.toList());
    }
}
