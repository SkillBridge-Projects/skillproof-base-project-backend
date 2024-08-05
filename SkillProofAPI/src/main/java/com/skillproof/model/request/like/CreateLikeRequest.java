package com.skillproof.model.request.like;

import com.skillproof.validators.Messages;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CreateLikeRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String userId;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private Long postId;
}
