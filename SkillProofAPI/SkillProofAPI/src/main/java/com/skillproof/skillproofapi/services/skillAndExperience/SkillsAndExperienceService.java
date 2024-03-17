package com.skillproof.skillproofapi.services.skillAndExperience;

import com.skillproof.skillproofapi.model.request.skillsAndExperience.CreateSkillsAndExperienceRequest;
import com.skillproof.skillproofapi.model.request.skillsAndExperience.SkillsAndExperienceResponse;

import java.util.List;

public interface SkillsAndExperienceService {
    SkillsAndExperienceResponse createSkillsAndExperience(CreateSkillsAndExperienceRequest createSkillsAndExperienceRequest);

    List<SkillsAndExperienceResponse> getSkillsAndExperiencesByUserId(Long userId);
}
