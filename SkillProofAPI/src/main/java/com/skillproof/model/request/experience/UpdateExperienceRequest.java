package com.skillproof.model.request.experience;

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
public class UpdateExperienceRequest {


    @NotEmptyOnly(label = ExperienceConstants.COMPANY_NAME)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "companyName", example = "skillbridge")
    private String companyName;

    @NotEmptyOnly(label = ExperienceConstants.DESIGNATION)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "designation", example = "junior developer")
    private String designation;

    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "description", example = "Worked as junior developer and user management application")
    private String description;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "startDate", example = "2023-03-01", type = "String", format = "YYYY-MM-DD")
    private LocalDate startDate;

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    @Schema(name = "endDate", example = "2024-03-01", type = "String", format = "YYYY-MM-DD")
    private LocalDate endDate;
}
