package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.model.request.connection.ConnectionResponse;
import com.skillproof.skillproofapi.model.request.connection.CreateConnectionRequest;
import com.skillproof.skillproofapi.services.connection.ConnectionService;
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
@Tag(name = "Connection", description = "Manages Connections of users in skillProof API")
public class ConnectionController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionController.class);

    private final ConnectionService connectionService;


    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping(value = "/users/{userId}/connections", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create connections for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = CreateConnectionRequest.class)))
            }
    )
    public ResponseEntity<ConnectionResponse> createConnectionForUser(@PathVariable Long userId, @RequestBody @Valid CreateConnectionRequest createConnectionRequest){
        LOG.info("Start of createConnectionForUser method.");
        ConnectionResponse connectionResponse = connectionService.createConnection(userId, createConnectionRequest);
        LOG.info("End of createConnectionForUser method.");
        return created(connectionResponse);
    }

    @GetMapping(value = "/users/{userId}/connections", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List connections for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = CreateConnectionRequest.class)))
            }
    )
    public ResponseEntity<List<ConnectionResponse>> listConnectionsForUser(@PathVariable Long userId){
        LOG.info("Start of listConnectionsForUser method.");
        List<ConnectionResponse> connectionResponses = connectionService.listConnectionsForUser(userId);
        LOG.info("End of listConnectionsForUser method.");
        return ok(connectionResponses);
    }
}
