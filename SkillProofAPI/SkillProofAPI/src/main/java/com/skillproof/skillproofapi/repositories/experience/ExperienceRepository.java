package com.skillproof.skillproofapi.repositories.experience;

import com.skillproof.skillproofapi.model.entity.Experience;

import java.util.List;

public interface ExperienceRepository {
    List<Experience> getExperienceByUserId(String userId);

    Experience createExperience(Experience experience);
}
