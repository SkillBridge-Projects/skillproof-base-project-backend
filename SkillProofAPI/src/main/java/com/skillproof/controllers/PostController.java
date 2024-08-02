package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.notification.NotificationResponse;
import com.skillproof.model.request.notification.UpdateNotificationRequest;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.services.AWSS3Service;
import com.skillproof.services.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = {"*"})
@Tag(name = "Post", description = "Manages Posts of users in skillProof App")
public class PostController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

    private final AWSS3Service AWSS3Service;
    private final PostService postService;

    public PostController(AWSS3Service AWSS3Service, PostService postService){
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
                                   @RequestParam(required = false) MultipartFile image,
                                   @RequestParam(required = false) MultipartFile video) throws Exception {
        return postService.createPost(content, userId, image, video);
    }

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Posts",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = PostResponse.class)))
            }
    )
    public ResponseEntity<List<PostResponse>> listAllPosts() {
        LOG.debug("Start of listAllPosts method.");
        List<PostResponse> posts = postService.listAllPosts();
        return ok(posts);
    }

    @PatchMapping(value = "/posts/{id}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Edit post of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = PostResponse.class)))
            }
    )
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
                                                   @RequestParam String content,
                                                   @RequestParam(required = false) MultipartFile image,
                                                   @RequestParam(required = false) MultipartFile video) throws Exception {
        PostResponse postResponse = postService.updatePost(id, content, image, video);
        return ok(postResponse);
    }

    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Post By Id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = PostResponse.class)))
            }
    )
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        LOG.debug("Start of getPostById method.");
        PostResponse post = postService.getPostById(id);
        LOG.debug("End of getPostById method.");
        return ok(post);
    }

    @DeleteMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete Post of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return ok();
    }
}

