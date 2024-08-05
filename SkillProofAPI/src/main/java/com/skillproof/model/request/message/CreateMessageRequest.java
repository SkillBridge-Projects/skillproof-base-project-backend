package com.skillproof.model.request.message;

import com.skillproof.validators.Messages;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@ToString
public class CreateMessageRequest {

    @NotNull(message = Messages.REQUIRED_PROPERTY)
    private Long conversationId;

    @NotNull(message = Messages.REQUIRED_PROPERTY)
    private String senderId;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 1000, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "content", example = "Hello, how are you?")
    private String content;
}

