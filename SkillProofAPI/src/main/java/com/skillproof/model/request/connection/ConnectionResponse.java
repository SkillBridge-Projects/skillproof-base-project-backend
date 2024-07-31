package com.skillproof.model.request.connection;

import com.skillproof.enums.ConnectionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ConnectionResponse extends CreateConnectionRequest {

    @Schema(name = "id", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(name = "createdDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdDate;

    @Schema(name = "updatedDate", example = "2024-03-01T12:00:00.000",
            type = "String", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedDate;

}
