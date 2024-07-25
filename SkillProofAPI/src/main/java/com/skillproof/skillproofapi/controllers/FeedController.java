//package com.skillproof.skillproofapi.controllers;
//
//
//import com.skillproof.skillproofapi.constants.SwaggerConstants;
//import com.skillproof.skillproofapi.model.entity.Comment;
//import com.skillproof.skillproofapi.model.entity.Post;
//import com.skillproof.skillproofapi.services.feed.FeedService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.AllArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@AllArgsConstructor
//@Tag(name = "Feed", description = "Manages News Feeds of users in skillProof App")
//public class FeedController {
//
//    private final FeedService feedService;
//
//    @CrossOrigin(origins = "*")
//    @GetMapping(value = "/in/{userId}/feed-posts", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "List Feed-Posts for User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
//                            content = @Content(schema = @Schema(implementation = Post.class)))
//            }
//    )
//    public Set<Post> getFeedPosts(@PathVariable Long userId) {
//        return feedService.getFeedPosts(userId);
//    }
//
//    @CrossOrigin(origins = "*")
//    @PostMapping(value = "/in/{userId}/feed/new-post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @Operation(summary = "Add New Post of an User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
//                            content = @Content(schema = @Schema(implementation = Post.class)))
//            }
//    )
//    public ResponseEntity newPost(@PathVariable Long userId, @RequestPart("object") Post post,
//                                  @RequestPart(value = "imageFile", required = false) MultipartFile[] files) throws IOException {
//        feedService.newPost(userId, post, files);
//        return ResponseEntity.ok("\"Post created with success!\"");
//    }
//
//    @CrossOrigin(origins = "*")
//    @PutMapping(value = "/in/{userId}/feed/post-interested/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Add New Post of an User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
//                            content = @Content(schema = @Schema(implementation = Post.class)))
//            }
//    )
//    public ResponseEntity newInterestedPost(@PathVariable Long userId, @PathVariable Long postId) {
//        feedService.newInterestedPost(userId, postId);
//        return ResponseEntity.ok("\"Interested in post created with success!\"");
//    }
//
//    @CrossOrigin(origins = "*")
//    @GetMapping(value = "/in/{userId}/feed/is-interested/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Interested Post of User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = Post.class)))
//            }
//    )
//    public ResponseEntity<?> isInterestedPost(@PathVariable Long userId, @PathVariable Long postId) {
//        return ResponseEntity.ok(feedService.isInterestedPost(userId, postId));
//    }
//
//    @CrossOrigin(origins = "*")
//    @PutMapping(value = "/in/{userId}/feed/new-comment/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Add New Comment for a Post",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
//                            content = @Content(schema = @Schema(implementation = Post.class)))
//            }
//    )
//    public ResponseEntity newComment(@PathVariable Long userId, @PathVariable Long postId, @RequestBody Comment comment) {
//        feedService.newComment(userId, postId, comment);
//        return ResponseEntity.ok("\"Comment in post created with success!\"");
//    }
//
//    @CrossOrigin(origins = "*")
//    @GetMapping(value = "/in/{userId}/recommended-posts", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Recommended Posts for User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
//                            content = @Content(schema = @Schema(implementation = Post.class)))
//            }
//    )
//    public List<Post> getRecommendedPosts(@PathVariable Long userId) {
//        return feedService.getRecommendedPosts(userId);
//    }
//
//}
