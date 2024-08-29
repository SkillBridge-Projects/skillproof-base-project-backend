package com.skillproof.model.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class PortfolioResponse {

    @Schema(name = "id", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(name = "videoUrl", example = "http://somevideo.mp4", accessMode = Schema.AccessMode.READ_ONLY)
    private String videoUrl;

    @Schema(name = "postIds", example = "[\"1\",\"2\"]", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Long> postIds;

    @Schema(name = "createdDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Schema(name = "updatedDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedDate;
}
