package com.skillproof.model.request.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class PostResponse extends CreatePostRequest {

    @Schema(name = "userEmail", example = "john.doe@email.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String userEmail;

    @Schema(name = "imageUrl", example = "https://skillproofmedia.s3.ap-south-1.amazonaws.com/172210xxxxxx_Capture.PNG",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String imageUrl;

    @Schema(name = "videoUrl", example = "https://skillproofmedia.s3.ap-south-1.amazonaws.com/172210xxxxxx_Video.MP4",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String videoUrl;

    @Schema(name = "id", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(name = "createdDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Schema(name = "updatedDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedDate;


}
