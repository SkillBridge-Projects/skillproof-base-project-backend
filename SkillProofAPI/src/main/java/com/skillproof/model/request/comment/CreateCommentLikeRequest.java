package com.skillproof.model.request.comment;

import com.skillproof.validators.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateCommentLikeRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private Long commentId;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String userId;
}

