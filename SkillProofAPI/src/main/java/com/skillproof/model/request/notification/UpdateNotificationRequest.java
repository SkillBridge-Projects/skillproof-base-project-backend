package com.skillproof.model.request.notification;

import com.skillproof.validators.Messages;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateNotificationRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private boolean isRead;
}
