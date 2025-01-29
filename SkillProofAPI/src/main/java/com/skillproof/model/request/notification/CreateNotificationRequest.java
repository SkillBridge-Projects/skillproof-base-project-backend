package com.skillproof.model.request.notification;

import com.skillproof.enums.NotificationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CreateNotificationRequest {

    @NotBlank(message = "isShown field is required.")
    private boolean isShown;

    @NotBlank(message = "NotificationType field is required.")
    private NotificationType type;

    @NotBlank(message = "UserId field is required.")
    private String userId;

    private Long connectionId;
    private Long commentId;
    private Long interestReactionId;

}
