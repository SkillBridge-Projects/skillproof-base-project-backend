package com.skillproof.model.request.portfolio;

import com.skillproof.validators.Messages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreatePortfolioMediaRequest {

    @Schema(name = "postId", example = "123")
    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private Long postId;

    @Schema(name = "mediaUrl", example = "http://someimage.png")
    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String mediaUrl;

    @Schema(name = "mediaIndex", example = "http://someimage.png")
    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private Integer mediaIndex;

    @Schema(name = "duration", example = "1.23")
    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private Float duration;
}

