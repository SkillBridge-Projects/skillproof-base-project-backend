package com.skillproof.services.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.InvalidRequestException;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Portfolio;
import com.skillproof.model.entity.PortfolioMedia;
import com.skillproof.model.entity.Post;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.comment.CommentResponse;
import com.skillproof.model.request.like.LikeResponse;
import com.skillproof.model.request.portfolio.CreatePortfolioMediaRequest;
import com.skillproof.model.request.portfolio.PortFolioMediaRequest;
import com.skillproof.model.request.portfolio.PortfolioMediaResponse;
import com.skillproof.model.request.post.Feed;
import com.skillproof.model.request.post.PortfolioResponse;
import com.skillproof.model.request.post.PostDTO;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.repositories.portfolio.PortfolioRepository;
import com.skillproof.repositories.post.PostRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.AWSS3Service;
import com.skillproof.services.comment.CommentService;
import com.skillproof.services.like.LikeService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostServiceImpl.class);

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PortfolioRepository portfolioRepository;
    private final AWSS3Service awss3Service;
    private final NotificationService notificationService;
    private final LikeService likeService;
    private final CommentService commentService;


    public PostServiceImpl(UserRepository userRepository, PostRepository postRepository,
                           PortfolioRepository portfolioRepository, AWSS3Service awss3Service,
                           NotificationService notificationService, LikeService likeService,
                           CommentService commentService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.portfolioRepository = portfolioRepository;
        this.awss3Service = awss3Service;
        this.notificationService = notificationService;
        this.likeService = likeService;
        this.commentService = commentService;
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

    @Override
    public Feed listAllFeed(List<String> userIds) {
        Feed feed = new Feed();
        for (String userId : userIds) {
            List<Post> posts = postRepository.findByUserId(userId);
            if (CollectionUtils.isNotEmpty(posts)) {
                List<PostDTO> postsForFeed = posts.stream().map(this::convertToDTO).collect(Collectors.toList());
                postsForFeed = postsForFeed.stream()
                        .sorted(Comparator.comparing(dto -> dto.getPost().getUpdatedDate(), Comparator.reverseOrder()))
                        .collect(Collectors.toList());
                feed.setPosts(postsForFeed);
            }
        }
        return feed;
    }

    @Override
    public PortfolioResponse addPortfolioVideo(String userId, String mediaRequestsJson, MultipartFile video) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PortFolioMediaRequest mediaRequests = objectMapper.readValue(mediaRequestsJson, PortFolioMediaRequest.class);
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }
        String videoUrl = awss3Service.uploadFile(video);

        Portfolio portfolio = createPortfolioEntity(user, videoUrl);
        Portfolio savedPortfolio = portfolioRepository.addPortfolioVideo(portfolio);

        List<PortfolioMedia> mediaList = mediaRequests.getPortfolioMediaRequests().stream()
                .map(mediaRequest -> {
                    Post post = postRepository.getPostById(mediaRequest.getPostId());
                    if (ObjectUtils.isEmpty(post)) {
                        LOG.error("Post with id {} not found.", mediaRequest.getPostId());
                        throw new ResourceNotFoundException(ObjectConstants.POST, ObjectConstants.ID, mediaRequest.getPostId());
                    }
                    return createPortfolioMediaEntity(mediaRequest, post, savedPortfolio);
                })
                .collect(Collectors.toList());

        List<PortfolioMedia> portfolioMediaList = portfolioRepository.addPortfolioMedia(mediaList);
        List<PortfolioMediaResponse> portfolioMediaResponses = getPortfolioMediaResponses(portfolioMediaList);
        return getPortfolioResponse(savedPortfolio, portfolioMediaResponses);
    }

    private PortfolioMedia createPortfolioMediaEntity(CreatePortfolioMediaRequest request, Post post,
                                                      Portfolio portfolio) {
        PortfolioMedia portfolioMedia = new PortfolioMedia();
        portfolioMedia.setPortfolio(portfolio);
        portfolioMedia.setPost(post);
        portfolioMedia.setMediaUrl(request.getMediaUrl());
        portfolioMedia.setMediaIndex(request.getMediaIndex());
        portfolioMedia.setDuration(request.getDuration());
        return portfolioMedia;
    }

    @Override
    public PortfolioResponse getPortfolioByUserId(String userId) {
        User user = userRepository.getUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            LOG.error("User with id {} not found.", userId);
            throw new UserNotFoundException(ObjectConstants.USER, userId);
        }

        Portfolio portfolio = portfolioRepository.getPortfolioByUserId(userId);
        if (ObjectUtils.isEmpty(portfolio)) {
            return null;
        }

        List<PortfolioMedia> portfolioMediaList = portfolioRepository.getPortfolioMediaById(portfolio.getId());
        List<PortfolioMediaResponse> portfolioMediaResponses = getPortfolioMediaResponses(portfolioMediaList);
        return getPortfolioResponse(portfolio, portfolioMediaResponses);
    }

    private List<PortfolioMediaResponse> getPortfolioMediaResponses(List<PortfolioMedia> portfolioMediaList) {
        return portfolioMediaList.stream().map(this::getPortfolioMediaResponse).collect(Collectors.toList());
    }

    private PortfolioMediaResponse getPortfolioMediaResponse(PortfolioMedia portfolioMedia){
        PortfolioMediaResponse response = ResponseConverter.copyProperties(portfolioMedia, PortfolioMediaResponse.class);
        response.setPortfolioId(portfolioMedia.getPortfolio().getId());
        response.setPostId(portfolioMedia.getPost().getId());
        return response;
    }

    @Override
    public PortfolioResponse updatePortfolio(Long id, MultipartFile video) throws Exception {
        Portfolio portfolio = portfolioRepository.getPortfolioById(id);
        if (ObjectUtils.isEmpty(portfolio)) {
            LOG.error("Portfolio with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.PORTFOLIO, ObjectConstants.ID, id);
        }

        String newVideoUrl = null;
        if (ObjectUtils.isNotEmpty(video)) {
            LOG.debug("Deleting old portfolio video of id {} from S3 bucket", id);
            awss3Service.deleteFile(portfolio.getVideoUrl());
            LOG.debug("Uploading new portfolio video for id {} to S3 bucket", id);
            newVideoUrl = awss3Service.uploadFile(video);
        }

        if (StringUtils.isNotEmpty(newVideoUrl)) {
            portfolio.setVideoUrl(newVideoUrl);
        }

        Portfolio updatedPortfolio = portfolioRepository.updatePortfolio(portfolio);
        return getPortfolioResponse(updatedPortfolio, new ArrayList<>());
    }

    @Override
    public void deletePortfolioById(Long id) {
        Portfolio portfolio = portfolioRepository.getPortfolioById(id);
        if (ObjectUtils.isEmpty(portfolio)) {
            LOG.error("Portfolio with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.PORTFOLIO, ObjectConstants.ID, id);
        }

        if (StringUtils.isNotEmpty(portfolio.getVideoUrl())) {
            LOG.debug("Deleting portfolio video of id {} from S3 bucket", id);
            awss3Service.deleteFile(portfolio.getVideoUrl());
        }
        portfolioRepository.deletePortfolioById(id);
    }

    private PortfolioResponse getPortfolioResponse(Portfolio portfolio, List<PortfolioMediaResponse> mediaResponses) {
        PortfolioResponse response = ResponseConverter.copyProperties(portfolio, PortfolioResponse.class);
        String preSignedVideoUrl = awss3Service.getPresignedUrl(response.getVideoUrl());
        response.setVideoUrl(preSignedVideoUrl);
        response.setPortfolioResponses(mediaResponses);
        return response;
    }

    private Portfolio createPortfolioEntity(User user, String videoUrl) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setVideoUrl(videoUrl);
        return portfolio;
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        List<LikeResponse> likes = likeService.listLikesByPostId(post.getId());
        List<CommentResponse> comments = commentService.listCommentsByPostId(post.getId());
        dto.setPost(getPostResponse(post));
        dto.setLikes(likes);
        dto.setComments(comments);
        return dto;
    }

    private PostResponse getPostResponse(Post post) {
        PostResponse postResponse = ResponseConverter.copyProperties(post, PostResponse.class);
        postResponse.setImageUrls(getPreSignedUrls(post.getImageUrl()));
        postResponse.setVideoUrls(getPreSignedUrls(post.getVideoUrl()));
        postResponse.setUserId(post.getUser().getId());
        postResponse.setUserEmail(post.getUser().getEmailAddress());
        postResponse.setUserName(post.getUser().getUserName());
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
