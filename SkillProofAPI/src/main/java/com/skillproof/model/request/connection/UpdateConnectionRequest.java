package com.skillproof.model.request.connection;

import com.skillproof.enums.ConnectionStatus;
import com.skillproof.validators.NotEmptyOnly;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateConnectionRequest {

    @NotEmptyOnly(label = "ConnectionStatus")
    @Schema(name = "connectionStatus", example = "PENDING")
    private ConnectionStatus connectionStatus;
}
