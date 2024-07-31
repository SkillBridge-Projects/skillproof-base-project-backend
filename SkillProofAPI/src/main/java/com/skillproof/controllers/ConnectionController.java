package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.connection.ConnectionResponse;
import com.skillproof.model.request.connection.CreateConnectionRequest;
import com.skillproof.model.request.connection.UpdateConnectionRequest;
import com.skillproof.model.request.user.UserResponse;
import com.skillproof.services.connection.ConnectionService;
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

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Connection", description = "Manages Connections of users in skillProof App")
public class ConnectionController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionController.class);

    private final ConnectionService connectionService;

    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping(value = "/connections", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create connections for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = ConnectionResponse.class)))
            }
    )
    public ResponseEntity<ConnectionResponse> createConnection(@RequestBody @Valid CreateConnectionRequest createConnectionRequest) {
        LOG.debug("Start of createConnection method.");
        ConnectionResponse connectionResponse = connectionService.createConnection(createConnectionRequest);
        LOG.debug("End of createConnection method.");
        return created(connectionResponse);
    }

    @GetMapping(value = "/connections/{followingUserId}/follower/{followerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get connection of followingUser for follower",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = ConnectionResponse.class)))
            }
    )
    public ResponseEntity<ConnectionResponse> getConnectionForUser(@PathVariable String followingUserId,
                                                                     @PathVariable String followerId) {
        LOG.debug("Start of getConnectionForUser method.");
        ConnectionResponse connectionResponse = connectionService.getConnectionForUser(followingUserId, followerId);
        LOG.debug("End of getConnectionForUser method.");
        return ok(connectionResponse);
    }

    @GetMapping(value = "/users/{userId}/connections",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List Connections for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = ConnectionResponse.class)))
            }
    )
    public ResponseEntity<List<UserResponse>> listConnectionsForUser(@PathVariable String userId) {
        LOG.debug("Start of listConnectionsForUser method.");
        List<UserResponse> users = connectionService.listConnectionsForUser(userId);
        LOG.debug("End of listConnectionsForUser method.");
        return ok(users);
    }

    @PatchMapping(value = "/connections/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update connection status of an user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = ConnectionResponse.class)))
            }
    )
    public ResponseEntity<ConnectionResponse> updateConnection(@PathVariable Long id,
                                                               @RequestBody @Valid UpdateConnectionRequest updateConnectionRequest) {
        ConnectionResponse connectionResponse = connectionService.updateConnection(id, updateConnectionRequest);
        return ok(connectionResponse);
    }

    @DeleteMapping(value = "/connections/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete connection of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteConnectionById(@PathVariable Long id) {
        connectionService.deleteConnectionById(id);
        return ok();
    }

    @GetMapping(value = "/connections/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Connection by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = ConnectionResponse.class)))
            }
    )
    public ResponseEntity<?> getConnectionById(@PathVariable Long id) {
        LOG.debug("Start of getConnectionById method.");
        ConnectionResponse connection = connectionService.getConnectionById(id);
        LOG.debug("End of getConnectionById method.");
        return ok(connection);
    }

    @GetMapping("connections/following/{userId}")
    @Operation(summary = "Get FollowingList for user by userId",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )
    public ResponseEntity<List<UserResponse>> getFollowingList(@PathVariable String userId) {
        List<UserResponse> followingList = connectionService.getFollowingList(userId);
        return ok(followingList);
    }

    @GetMapping("connections/followers/{userId}")
    @Operation(summary = "Get FollowersList for user by userId",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
            }
    )
    public ResponseEntity<List<UserResponse>> getFollowersList(@PathVariable String userId) {
        List<UserResponse> followersList = connectionService.getFollowersList(userId);
        return ok(followersList);
    }
}
