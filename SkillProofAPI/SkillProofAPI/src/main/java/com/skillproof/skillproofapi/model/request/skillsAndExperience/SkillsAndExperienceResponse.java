package com.skillproof.skillproofapi.model.request.skillsAndExperience;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SkillsAndExperienceResponse extends CreateSkillsAndExperienceRequest {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
