package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.notification.CreateNotificationRequest;
import com.skillproof.model.request.notification.NotificationResponse;
import com.skillproof.model.request.notification.UpdateNotificationRequest;
import com.skillproof.services.notification.NotificationService;
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
@Tag(name = "Notification", description = "Manages Notifications of users in skillProof App")
public class NotificationController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create notification for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = CreateNotificationRequest.class)))
            }
    )
    public ResponseEntity<NotificationResponse> createNotification(
            @RequestBody @Valid CreateNotificationRequest createNotificationRequest) {
        LOG.debug("Start of createNotification method.");
        NotificationResponse notificationResponse = notificationService.createNotification(createNotificationRequest);
        LOG.debug("End of createNotification method.");
        return created(notificationResponse);
    }

    @GetMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Notifications",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = NotificationResponse.class)))
            }
    )
    public ResponseEntity<List<NotificationResponse>> listAllNotifications() {
        LOG.debug("Start of listAllNotifications method.");
        List<NotificationResponse> notifications = notificationService.listAllNotifications();
        return ok(notifications);
    }

    @GetMapping(value = "/users/{userId}/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List Notifications for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = NotificationResponse.class)))
            }
    )
    public ResponseEntity<List<NotificationResponse>> listNotificationsForUser(@PathVariable String userId) {
        LOG.debug("Start of listNotificationsForUser method.");
        List<NotificationResponse> notifications = notificationService.listNotificationsForUser(userId);
        return ok(notifications);
    }

    @PatchMapping(value = "/notifications/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Update notification status",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
                            content = @Content(schema = @Schema(implementation = NotificationResponse.class)))
            }
    )
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable Long id,
                                                                   @RequestBody @Valid UpdateNotificationRequest updateNotificationRequest) {
        NotificationResponse notificationResponse = notificationService.updateNotification(id, updateNotificationRequest);
        return ok(notificationResponse);
    }

    @GetMapping(value = "/notifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Notification By Id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = NotificationResponse.class)))
            }
    )
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        LOG.debug("Start of getNotificationById method.");
        NotificationResponse notification = notificationService.getNotificationById(id);
        LOG.debug("End of getNotificationById method.");
        return ok(notification);
    }

    @DeleteMapping(value = "/notifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete notification of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteNotificationById(@PathVariable Long id) {
        notificationService.deleteNotificationById(id);
        return ok();
    }
}
