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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@Tag(name = "Post", description = "Manage posts of user in SkillProof App")
public class PostController extends AbstractController{

    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

    private final AWSS3Service awss3Service;
    private final PostService postService;

    public PostController(AWSS3Service awss3Service, PostService postService) {
        this.awss3Service = awss3Service;
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
}
