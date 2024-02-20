package com.skillproof.skillproofapi.model.request.notification;

import com.skillproof.skillproofapi.enumerations.NotificationType;
import lombok.Data;

@Data
public class NotificationResponse {

    private Long id;
    private Boolean isShown = false;
    private NotificationType type;
    private Long userId;
    private Long connectionRequestId;
    private Long newCommentId;
    private Long newInterestId;

    public NotificationResponse(Long id, Boolean isShown, NotificationType type, Long userId,
                           Long connectionRequestId, Long newCommentId, Long newInterestId) {
        this.id = id;
        this.isShown = isShown;
        this.type = type;
        this.userId = userId;
        this.connectionRequestId = connectionRequestId;
        this.newCommentId = newCommentId;
        this.newInterestId = newInterestId;
    }
}
