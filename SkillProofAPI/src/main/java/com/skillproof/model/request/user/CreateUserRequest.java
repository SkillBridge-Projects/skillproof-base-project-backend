package com.skillproof.model.request.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Size(min = 10, max = 10, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "phone", example = "9087654321", format = "phone")
    private Long phone;

    @Schema(name = "bio", example = "I am john...", format = "bio")
    private String bio;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "city", example = "bangalore", format = "city")
    private String city;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "profilePicture", example = "http://someimage.png", accessMode = Schema.AccessMode.READ_ONLY)
    private String profilePicture;

    @NotNull(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "role", example = "EMPLOYEE")
    private RoleType role;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "skills", example = "java", format = "skills")
    private String skills;

}
