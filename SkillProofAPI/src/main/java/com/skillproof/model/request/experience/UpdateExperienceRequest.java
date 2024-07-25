package com.skillproof.model.request.experience;

import com.skillproof.constants.ExperienceConstants;
import com.skillproof.validators.Messages;
import com.skillproof.validators.NotEmptyOnly;
import com.skillproof.validators.RegEx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateExperienceRequest {


    @NotEmptyOnly(label = ExperienceConstants.COMPANY_NAME)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "companyName", example = "skillbridge", format = "companyname")
    private String companyName;

    @NotEmptyOnly(label = ExperienceConstants.DESIGNATION)
    @Size(max = 100, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "designation", example = "junior developer", format = "designation")
    private String designation;

    @Size(max = 250, message = Messages.SIZE_VALIDATION_PROPERTY)
    @Pattern(regexp = RegEx.STRING_CHARACTERS_REGEX, message = Messages.NO_WHITESPACE_PROPERTY)
    @Schema(name = "description", example = "Worked as junior developer and user management application",
            format = "description")
    private String description;

    @NotEmptyOnly(label = ExperienceConstants.EXPERIENCE)
    @Schema(name = "experience", example = "2.0", format = "experience")
    private float experience;
}
