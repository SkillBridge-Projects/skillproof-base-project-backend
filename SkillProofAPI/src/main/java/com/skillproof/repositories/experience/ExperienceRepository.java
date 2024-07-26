package com.skillproof.repositories.experience;

import com.skillproof.model.entity.Experience;

import java.util.List;

public interface ExperienceRepository {
    List<Experience> getExperienceByUserId(String userId);

    Experience createExperience(Experience experience);

    Experience getExperienceById(Long id);

    void deleteExperienceById(Long id);

    Experience updateExperience(Experience experience);

    List<Experience> listAllExperienceDetails();
}
