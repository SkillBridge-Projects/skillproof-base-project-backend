package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.entity.Post;
import com.skillproof.model.request.post.PostResponse;
import com.skillproof.services.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@Tag(name = "Post", description = "Manage posts of user in SkillProof App")
public class PostController extends AbstractController{

    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
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

    @PostMapping(value = "/{postId}/share/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Send Post to the user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = void.class)))
            }
    )
    public ResponseEntity<?> sharePost(@PathVariable Long postId, @PathVariable String userId) {
        try {
            Post sharedPost = postService.sharePostAndNotify(postId, userId);
            return ResponseEntity.ok(sharedPost);
        } catch (MessagingException e){
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Failed to send email notification.");
        } catch (Exception e){
            return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body("Failed to share the post");
        }
    }

    @DeleteMapping(value = "/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete post by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ok();
    }
}