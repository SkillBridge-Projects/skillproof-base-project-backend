package com.skillproof.skillproofapi.repositories.skillsAndExperience;

import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;

import java.util.List;

public interface SkillsAndExperienceRepository {
    List<SkillsAndExperience> getSkillsAndExperiencesByUserId(Long userId);

    SkillsAndExperience createSkillsAndExperience(SkillsAndExperience skillsAndExperience);
}
