package com.skillproof.skillproofapi.model.request.experience;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExperienceResponse extends CreateExperienceRequest {

    @Schema(name = "userEmail", example = "john.doe@email.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String userEmail;

    @Schema(name = "id", example = "ad23ua63gaf7a", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(name = "createdDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Schema(name = "updatedDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedDate;
}
