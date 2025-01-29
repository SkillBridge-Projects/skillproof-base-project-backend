package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.enums.FriendRequestStatus;
import com.skillproof.services.friendRequest.FriendRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/friend-requests")
@AllArgsConstructor
@Tag(name = "Friend Request", description = "We can send and receive Friend requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @PostMapping(value = "/friendRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Sending a friend request",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    public ResponseEntity<String> sendFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        return ResponseEntity.ok(friendRequestService.sendFriendRequest(senderId, receiverId));
    }

    @PostMapping(value = "/updateStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Friend Request Status",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = Void.class)))
            }
    )
    public ResponseEntity<String> updateFriendRequestStatus(@RequestParam Long requestId, @RequestParam FriendRequestStatus status) {
        return ResponseEntity.ok(friendRequestService.updateFriendRequestStatus(requestId, status));
    }
}