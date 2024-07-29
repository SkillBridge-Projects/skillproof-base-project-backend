package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.services.AWSS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = {"*"})
@Tag(name = "Post", description = "Manages Posts in skillProof App")
public class PostController {

//    @Autowired
//    private PostService postService;

    private final AWSS3Service AWSS3Service;

    public PostController(AWSS3Service AWSS3Service){
        this.AWSS3Service = AWSS3Service;
    }

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Save Post of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = void.class)))
            }
    )
    public void createPost(@RequestParam String content,
                           @RequestParam(required = false) MultipartFile image,
                           @RequestParam(required = false) MultipartFile video) throws Exception {
        String imageUrl = image != null ? AWSS3Service.uploadFile(image) : null;
        String videoUrl = video != null ? AWSS3Service.uploadFile(video) : null;

        System.out.println(imageUrl);
        System.out.println(videoUrl);
//        Post post = new Post();
//        post.setContent(content);
//        post.setImageUrl(imageUrl);
//        post.setVideoUrl(videoUrl);
//
//        return postService.createPost(post);
    }

    // Other methods...
}

