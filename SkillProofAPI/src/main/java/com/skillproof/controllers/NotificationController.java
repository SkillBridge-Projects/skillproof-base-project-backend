package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.enums.NotificationStatus;
import com.skillproof.model.entity.Notification;
import com.skillproof.services.notification.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Notification", description = "Manages Notifications of users in SkillProof App")
public class NotificationController {

    private final NotificationServiceImpl notificationService;

    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(value = "/create-notification", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Creating notification for a user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = void.class)))
            }
    )
    public ResponseEntity<String> sendNotification(@RequestParam String userId, @RequestParam String message) {
        try {
            notificationService.sendNotification(userId, message);
            return ResponseEntity.ok("Notification sent successfully and updated the status");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while sending notification: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{notificationId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Notification status",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = Notification.class)))
            }
    )
    public ResponseEntity<String> updateNotificationStatus(@PathVariable Long notificationId, @RequestParam NotificationStatus status) {
        try {
            notificationService.updateNotificationStatus(notificationId, status);
            return ResponseEntity.ok("Notification status updated Successfully");
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body("Invalid status value: " + status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Notification not found: " + e.getMessage());
        }
    }
}