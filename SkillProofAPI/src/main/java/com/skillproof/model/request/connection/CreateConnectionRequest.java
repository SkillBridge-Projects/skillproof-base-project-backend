package com.skillproof.model.request.connection;

import com.skillproof.enums.ConnectionStatus;
import com.skillproof.validators.Messages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CreateConnectionRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String following;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String follower;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "connectionStatus", example = "PENDING")
    private ConnectionStatus connectionStatus;
}
