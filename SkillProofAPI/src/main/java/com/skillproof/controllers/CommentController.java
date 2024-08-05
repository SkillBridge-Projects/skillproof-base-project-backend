package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.comment.CommentResponse;
import com.skillproof.model.request.comment.CreateCommentRequest;
import com.skillproof.model.request.comment.UpdateCommentRequest;
import com.skillproof.services.comment.CommentService;
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
@Tag(name = "Comment", description = "Stores Comments of posts of users in skillProof App")
public class CommentController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Comment for a post of a user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = CommentResponse.class)))
            }
    )
    public ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CreateCommentRequest createCommentRequest) {
        LOG.debug("Start of createComment method.");
        CommentResponse commentResponse = commentService.createComment(createCommentRequest);
        LOG.debug("End of createComment method.");
        return created(commentResponse);
    }

    @GetMapping(value = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "list Comments for post",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = CommentResponse.class)))
            }
    )
    public ResponseEntity<?> listCommentsByPostId(@PathVariable Long postId) {
        LOG.debug("Start of listCommentsByPostId method.");
        List<CommentResponse> commentResponse = commentService.listCommentsByPostId(postId);
        if (CollectionUtils.isEmpty(commentResponse)) {
            LOG.debug("End of listCommentsByPostId method.");
            return noContent();
        }
        LOG.debug("End of listCommentsByPostId method.");
        return ok(commentResponse);
    }

    @GetMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Comment by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = CommentResponse.class)))
            }
    )
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        LOG.debug("Start of getCommentById method.");
        CommentResponse CommentResponse = commentService.getCommentById(id);
        LOG.debug("End of getCommentById method.");
        return ok(CommentResponse);
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Comments of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = CommentResponse.class)))
            }
    )
    public ResponseEntity<List<CommentResponse>> listAllComments() {
        List<CommentResponse> commentResponses = commentService.listAllComments();
        return ok(commentResponses);
    }

    @PatchMapping(value = "/comments/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update comment of a post of a user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = CommentResponse.class)))
            }
    )
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id,
                                                         @RequestBody UpdateCommentRequest updateCommentRequest) {
        LOG.debug("Start of updateComment method.");
        CommentResponse commentResponse = commentService.updateComment(id, updateCommentRequest);
        LOG.debug("End of updateComment method.");
        return ok(commentResponse);
    }

    @DeleteMapping(value = "/comments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete comment of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return ok();
    }
}
