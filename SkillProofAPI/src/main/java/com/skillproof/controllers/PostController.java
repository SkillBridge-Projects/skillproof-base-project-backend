package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.services.AWSS3Service;
import com.skillproof.services.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin(origins = {"*"})
@Tag(name = "Post", description = "Manages Posts of users in skillProof App")
public class PostController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

    private final AWSS3Service AWSS3Service;
    private final PostService postService;

    public PostController(AWSS3Service AWSS3Service, PostService postService) {
        this.AWSS3Service = AWSS3Service;
        this.postService = postService;
    }

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Save Post of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = PostResponse.class)))
            }
    )
    public PostResponse createPost(@RequestParam String content,
                                   @RequestParam String userId,
                                   @RequestParam(required = false) List<MultipartFile> images,
                                   @RequestParam(required = false) List<MultipartFile> videos) throws Exception {
        return postService.createPost(content, userId, images, videos);
    }

//    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "List All Posts",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
//                            content = @Content(schema = @Schema(implementation = PostResponse.class)))
//            }
//    )
//    public ResponseEntity<List<PostResponse>> listAllPosts() {
//        LOG.debug("Start of listAllPosts method.");
//        List<PostResponse> posts = postService.listAllPosts();
//        return ok(posts);
//    }
//
//    @GetMapping(value = "/news-feed", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "List All Feed",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
//                            content = @Content(schema = @Schema(implementation = PostDTO.class)))
//            }
//    )
//    public ResponseEntity<Feed> listAllFeed(@RequestParam List<String> userIds) {
//        LOG.debug("Start of listAllFeed method.");
//        Feed feed = postService.listAllFeed(userIds);
//        return ok(feed);
//    }
//
//    @PatchMapping(value = "/posts/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @Operation(summary = "Edit post of an user",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = PostResponse.class)))
//            }
//    )
//    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
//                                                   @RequestParam String content,
//                                                   @RequestParam(required = false) List<MultipartFile> images,
//                                                   @RequestParam(required = false) List<MultipartFile> videos) throws Exception {
//        PostResponse postResponse = postService.updatePost(id, content, images, videos);
//        return ok(postResponse);
//    }
//
//    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Post By Id",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
//                            content = @Content(schema = @Schema(implementation = PostResponse.class)))
//            }
//    )
//    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
//        LOG.debug("Start of getPostById method.");
//        PostResponse post = postService.getPostById(id);
//        LOG.debug("End of getPostById method.");
//        return ok(post);
//    }
//
//    @DeleteMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Delete Post of an user by id",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
//            }
//    )
//    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
//        postService.deletePostById(id);
//        return ok();
//    }
//
//    @PostMapping(value = "/posts/{userId}/portfolio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary = "Save Portfolio video of an user",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
//                            content = @Content(schema = @Schema(implementation = PortfolioResponse.class)))
//            }
//    )
//    public ResponseEntity<?> addPortfolioVideo(@PathVariable(name = "userId") String userId,
//                                               @RequestParam String mediaRequestsJson,
//                                               @RequestParam MultipartFile video) throws Exception {
//        PortfolioResponse response = postService.addPortfolioVideo(userId, mediaRequestsJson, video);
//        return ok(response);
//    }
//
//    @GetMapping(value = "/posts/{userId}/portfolio", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Portfolio of a user By Id",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
//                            content = @Content(schema = @Schema(implementation = PortfolioResponse.class)))
//            }
//    )
//    public ResponseEntity<?> getPortfolioByUserId(@PathVariable(name = "userId") String userId) {
//        LOG.debug("Start of getPortfolioByUserId method.");
//        PortfolioResponse portfolio = postService.getPortfolioByUserId(userId);
//        if (ObjectUtils.isEmpty(portfolio)) {
//            return noContent();
//        }
//        LOG.debug("End of getPortfolioByUserId method.");
//        return ok(portfolio);
//    }
//
//    @PatchMapping(value = "/posts/{id}/portfolio", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @Operation(summary = "Edit portfolio of an user",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = PortfolioResponse.class)))
//            }
//    )
//    public ResponseEntity<PortfolioResponse> updatePortfolio(@PathVariable Long id,
//                                                             @RequestParam String mediaRequestsJson,
//                                                             @RequestParam MultipartFile video) throws Exception {
//        PortfolioResponse portfolio = postService.updatePortfolio(id, mediaRequestsJson, video);
//        return ok(portfolio);
//    }
//
//    @DeleteMapping(value = "/portfolio/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Delete Portfolio of an user by id",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
//            }
//    )
//    public ResponseEntity<?> deletePortfolioById(@PathVariable Long id) {
//        postService.deletePortfolioById(id);
//        return ok();
//    }
}

