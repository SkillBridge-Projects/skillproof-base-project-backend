package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.like.CreateLikeRequest;
import com.skillproof.model.request.like.LikeResponse;
import com.skillproof.services.like.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Like", description = "Stores Likes of posts of users in skillProof App")
public class LikeController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(LikeController.class);

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Like for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = LikeResponse.class)))
            }
    )
    public ResponseEntity<LikeResponse> createLike(@RequestBody @Valid CreateLikeRequest createLikeRequest) {
        LOG.debug("Start of createLike method.");
        LikeResponse likeResponse = likeService.createLike(createLikeRequest);
        LOG.debug("End of createLike method.");
        return created(likeResponse);
    }

    @GetMapping(value = "/posts/{postId}/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "list Likes for post",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = LikeResponse.class)))
            }
    )
    public ResponseEntity<?> listLikesByPostId(@PathVariable Long postId) {
        LOG.debug("Start of listLikesByPostId method.");
        List<LikeResponse> likeResponse = likeService.listLikesByPostId(postId);
        if (CollectionUtils.isEmpty(likeResponse)) {
            LOG.debug("End of listLikesByPostId method.");
            return noContent();
        }
        LOG.debug("End of listLikesByPostId method.");
        return ok(likeResponse);
    }

    @GetMapping(value = "/likes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Like by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = LikeResponse.class)))
            }
    )
    public ResponseEntity<?> getLikeById(@PathVariable Long id) {
        LOG.debug("Start of getLikeById method.");
        LikeResponse LikeResponse = likeService.getLikeById(id);
        LOG.debug("End of getLikeById method.");
        return ok(LikeResponse);
    }

    @GetMapping(value = "/likes", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Likes of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = LikeResponse.class)))
            }
    )
    public ResponseEntity<List<LikeResponse>> listAllLikes() {
        List<LikeResponse> likeResponses = likeService.listAllLikes();
        return ok(likeResponses);
    }

    @DeleteMapping(value = "/likes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete like of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteLikeById(@PathVariable Long id) {
        likeService.deleteLikeById(id);
        return ok();
    }
}
