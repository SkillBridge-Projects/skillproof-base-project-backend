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
public class PostResponse extends CreatePostRequest {

    @Schema(name = "userEmail", example = "john.doe@email.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String userEmail;

    @Schema(name = "userName", example = "john doe", accessMode = Schema.AccessMode.READ_ONLY)
    private String userName;

    @Schema(name = "profilePicture", example = "http://someimage.png", accessMode = Schema.AccessMode.READ_ONLY)
    private String profilePicture;

    @Schema(name = "imageUrl", example = "[\"https://skillproofmedia.s3.ap-south-1.amazonaws.com/172210xxxxxx_Capture.PNG\"]",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<String> imageUrls;

    @Schema(name = "videoUrl", example = "[\"https://skillproofmedia.s3.ap-south-1.amazonaws.com/172210xxxxxx_Video.MP4\"]",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<String> videoUrls;

    @Schema(name = "id", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(name = "createdDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Schema(name = "updatedDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedDate;
}
