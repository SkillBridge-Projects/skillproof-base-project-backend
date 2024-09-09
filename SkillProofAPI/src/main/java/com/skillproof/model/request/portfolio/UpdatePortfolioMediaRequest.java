package com.skillproof.model.request.portfolio;

import com.skillproof.validators.Messages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdatePortfolioMediaRequest {

    @Schema(name = "id", example = "123")
    private Long id;

    @Schema(name = "mediaUrl", example = "http://someimage.png")
    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String url;

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

