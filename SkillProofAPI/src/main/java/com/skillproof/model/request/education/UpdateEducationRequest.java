package com.skillproof.model.request.education;

import com.skillproof.constants.EducationConstants;
import com.skillproof.constants.ExperienceConstants;
import com.skillproof.validators.Messages;
import com.skillproof.validators.NotEmptyOnly;
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
public class UpdateEducationRequest {

    @NotEmptyOnly(label = EducationConstants.UNIVERSITY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "university", example = "Cambridge")
    private String university;

    @NotEmptyOnly(label = EducationConstants.UNIVERSITY)
    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "collegeOrSchool", example = "Cambridge")
    private String collegeOrSchool;

    @NotEmptyOnly(label = EducationConstants.UNIVERSITY)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "degree", example = "Bachelor of Technology")
    private String degree;

    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "description", example = "Completed B.Tech in the stream of Computer Science")
    private String description;

    @NotEmptyOnly(label = EducationConstants.GRADE)
    @Schema(name = "grade", example = "10.0")
    private Float grade;

    @Schema(name = "startDate", example = "2023-03-01", type = "String", format = "YYYY-MM-DD")
    private LocalDate startDate;

    @Schema(name = "endDate", example = "2024-03-01", type = "String", format = "YYYY-MM-DD")
    private LocalDate endDate;
}
