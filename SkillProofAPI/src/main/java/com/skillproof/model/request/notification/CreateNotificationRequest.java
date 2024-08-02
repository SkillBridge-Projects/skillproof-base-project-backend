package com.skillproof.model.request.notification;

import com.skillproof.enums.NotificationType;
import com.skillproof.validators.Messages;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class CreateNotificationRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private boolean isRead;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String followerId;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "message", example = "John.Doe has sent you a connection request.")
    private String message;

    @NotNull(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "notificationType", example = "POST")
    private NotificationType notificationType;

    @Schema(hidden = true)
    private String profilePicture;

    @Schema(hidden = true)
    private String followingId;

    @Schema(hidden = true)
    private String followingUserName;
}
