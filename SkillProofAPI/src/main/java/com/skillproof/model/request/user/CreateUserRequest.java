
package com.skillproof.model.request.user;

import com.skillproof.enums.RoleType;
import com.skillproof.validators.Messages;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateUserRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.EMAIL_REGEX, message = Messages.EMAIL_VALIDATION_PROPERTY)
    @Schema(name = "emailAddress", example = "john.doe@email.com", format = "email")
    private String emailAddress;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "firstName", example = "john", format = "firstname")
    private String firstName;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "lastName", example = "john", format = "lastname")
    private String lastName;

    @Schema(name = "password", format = "password")
    private String password;

    @Size(max = 20, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "phone", example = "9087654321", format = "phone")
    private String phone;

    @Schema(name = "bio", example = "I am john...", format = "bio")
    private String bio;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "city", example = "bangalore", format = "city")
    private String city;

    @NotNull(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "role", example = "EMPLOYEE")
    private RoleType role;

    @Valid
    @NotNull(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "skills", example = "[\"Java\",\"SQL\"]")
    private List<@NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY) String> skills;
}
