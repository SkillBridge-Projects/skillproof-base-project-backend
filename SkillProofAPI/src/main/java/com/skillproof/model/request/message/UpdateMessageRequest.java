package com.skillproof.model.request.message;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.validators.Messages;
import com.skillproof.validators.NotEmptyOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateMessageRequest {

    @NotEmptyOnly(label = ObjectConstants.CONVERSATION)
    @Size(max = 1000, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "content", example = "Updated message content")
    private String content;
}
