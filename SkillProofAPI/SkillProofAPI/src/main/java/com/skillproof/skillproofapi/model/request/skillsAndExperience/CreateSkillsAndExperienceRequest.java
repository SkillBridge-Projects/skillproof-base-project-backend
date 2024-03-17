package com.skillproof.skillproofapi.model.request.skillsAndExperience;

import com.skillproof.skillproofapi.enumerations.SkillType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CreateSkillsAndExperienceRequest {

    @NotBlank(message = "SkillType field is required")
    private SkillType skillType;

    @Size(max = 100, message = "Characters in description should not be greater then 100")
    private String description;

    @NotBlank(message = "isPublic field is required")
    private Boolean isPublic;

    @NotBlank(message = "userId field is required")
    private Long userId;
}
