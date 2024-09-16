package com.skillproof.model.request.comment;

import com.skillproof.validators.Messages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateCommentReplyRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private Long commentId;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String userId;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 1000, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "content", example = "Congratulations on your work")
    private String content;
}

