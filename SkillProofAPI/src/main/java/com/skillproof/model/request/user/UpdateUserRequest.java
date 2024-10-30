package com.skillproof.model.request.user;

import com.skillproof.constants.UserConstants;
import com.skillproof.validators.Messages;
import com.skillproof.validators.NotEmptyOnly;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
public class UpdateUserRequest {

    @NotEmptyOnly(label = UserConstants.FIRSTNAME)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "firstName", example = "john", format = "firstname")
    private String firstName;

    @NotEmptyOnly(label = UserConstants.LASTNAME)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "lastName", example = "john", format = "lastname")
    private String lastName;

    @Size(max = 20, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "phone", example = "9087654321", format = "phone")
    private Long phone;

    @Schema(name = "bio", example = "I am john...", format = "bio")
    private String bio;

    @NotEmptyOnly(label = UserConstants.CITY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "city", example = "bangalore", format = "city")
    private String city;

//    @Schema(name = "skills", example = "[\"Java\",\"SQL\"]")
//    private List<@NotEmpty(message = Messages.NO_EMPTY_PROPERTY)
//    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY) String> skills;
}