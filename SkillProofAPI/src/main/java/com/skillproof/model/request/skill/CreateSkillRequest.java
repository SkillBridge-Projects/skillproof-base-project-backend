package com.skillproof.model.request.skill;

import com.skillproof.validators.Messages;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateSkillRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "technology", example = "Java")
    private String technology;

    @Valid
    @NotNull(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "tools", example = "[\"IntelliJ\",\"Eclipse\"]")
    private List<@NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY) String> tools;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String userId;
}
