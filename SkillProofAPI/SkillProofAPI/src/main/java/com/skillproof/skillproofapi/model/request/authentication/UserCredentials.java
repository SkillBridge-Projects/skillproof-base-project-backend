package com.skillproof.skillproofapi.model.request.authentication;

import com.skillproof.skillproofapi.validators.Messages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class UserCredentials {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "userName", example = "john@email.com", format = "username")
    private String userName;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "password", format = "password")
    private String password;
}
