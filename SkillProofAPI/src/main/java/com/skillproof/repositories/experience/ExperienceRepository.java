package com.skillproof.repositories.experience;

import com.skillproof.model.entity.Experience;

import java.util.List;

public interface ExperienceRepository {
    List<Experience> getExperienceByUserId(String userId);

    Experience createExperience(Experience experience);
}
