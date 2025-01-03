package com.skillproof.model.request.education;

import com.skillproof.validators.Messages;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreateEducationRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "university", example = "Cambridge")
    private String university;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "college", example = "Cambridge")
    private String college;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "degree", example = "Bachelor of Science and Computer")
    private String degree;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Schema(name = "description", example = "Completed in degree in the stream of Bsc Electronics")
    private String description;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "grade", example = "10.0")
    private Float grade;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "startDate", example = "2023-03-01", type = "String", format = "YYYY-MM-DD")
    private LocalDate startDate;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "endDate", example = "2024-03-01", type = "String", format = "YYYY-MM-DD")
    private LocalDate endDate;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private String userId;
}
