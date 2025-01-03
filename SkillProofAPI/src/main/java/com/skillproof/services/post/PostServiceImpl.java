package com.skillproof.services.post;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.InvalidRequestException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Post;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.repositories.post.PostRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.AWSS3Service;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository, AWSS3Service awss3Service) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.awss3Service = awss3Service;
    }

    @Override
    public PostResponse createPost(String content, String userId, List<MultipartFile> images, List<MultipartFile> videos) throws Exception {
        LOG.debug("Start of create post message");
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found", userId);
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

        LOG.debug("End of createPosr method");
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

    private PostResponse getPostResponse(Post post) {
        PostResponse postResponse = ResponseConverter.copyProperties(post, PostResponse.class);
        postResponse.setImageUrls(getPreSignedUrls(post.getImageUrl()));
        postResponse.setVideoUrls(getPreSignedUrls(post.getVideoUrl()));
        postResponse.setUserId(post.getUser().getId());
        postResponse.setUserEmail(post.getUser().getEmailAddress());
        postResponse.setUserName(post.getUser().getUsername());
        postResponse.setProfilePicture(awss3Service.getPresignedUrl(post.getUser().getProfilePicture()));
        return postResponse;
    }

    private List<String> getPreSignedUrls(String baseUrl) {
        if (baseUrl != null) {
            return Arrays.stream(baseUrl.split(","))
                    .map(awss3Service::getPresignedUrl)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<PostResponse> getPostResponseList(List<Post> posts) {
        return posts.stream()
                .map(this::getPostResponse)
                .collect(Collectors.toList());
    }
}
