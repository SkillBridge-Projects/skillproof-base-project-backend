//package com.skillproof.skillproofapi.controllers;
//
//
//import com.skillproof.skillproofapi.constants.SwaggerConstants;
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.services.network.NetworkService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Set;
//
//import static com.google.common.collect.Lists.reverse;
//
//@RestController
//@CrossOrigin(origins = "*")
//@Tag(name = "Network", description = "Manages Network of users in skillProof App")
//public class NetworkController {
//
//    private final NetworkService networkService;
//
//    public NetworkController(NetworkService networkService) {
//        this.networkService = networkService;
//    }
//
//    @GetMapping(value = "/in/{userId}/search/{searchQuery}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Search User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public List<User> search(@PathVariable Long userId, @PathVariable String searchQuery) {
//        List<User> searchResults = networkService.search(userId, searchQuery);
//        return reverse(searchResults);
//    }
//
//    @GetMapping(value = "/in/{userId}/network", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get Network of an User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public Set<User> getNetwork(@PathVariable Long userId) {
//        return networkService.getNetwork(userId);
//    }
//
//    @GetMapping(value = "/in/{userId}/request/{otherUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Send Connection Request to User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public ResponseEntity<String> hasSendRequest(@PathVariable Long userId, @PathVariable Long otherUserId) {
//        Boolean result = networkService.hasSendRequest(userId, otherUserId);
//        return ResponseEntity.ok(result.toString());
//    }
//
//    @PutMapping(value = "/in/{userId}/new-connection/{newUserId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Add Connections to User ",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public ResponseEntity addToConnections(@PathVariable Long userId, @PathVariable Long newUserId) {
//        networkService.addToConnections(userId, newUserId);
//        return ResponseEntity.ok("\"New connection added with success!\"");
//    }
//
//    @PutMapping(value = "/in/{userId}/notifications/{connectionId}/accept-connection",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Accept Connection of an User",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = User.class)))
//            }
//    )
//    public ResponseEntity acceptConnection(@PathVariable Long userId, @PathVariable Long connectionId) {
//        networkService.acceptConnection(userId, connectionId);
//        return ResponseEntity.ok("\"Connection accepted with success!\"");
//    }
//}
