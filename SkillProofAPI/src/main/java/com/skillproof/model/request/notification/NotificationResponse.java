package com.skillproof.model.request.notification;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NotificationResponse extends CreateNotificationRequest {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
