package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.model.request.notification.NotificationResponse;
import com.skillproof.skillproofapi.services.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Notification", description = "Manages Notifications of users in skillProof App")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/users/{userId}/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List Notifications for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = NotificationResponse.class)))
            }
    )
    public List<NotificationResponse> listNotificationsForUser(@PathVariable Long userId) {
        return notificationService.listNotifications(userId);
    }
}
