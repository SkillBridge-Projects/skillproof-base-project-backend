package com.skillproof.services.experience;

import com.skillproof.model.request.experience.CreateExperienceRequest;
import com.skillproof.model.request.experience.ExperienceResponse;
import com.skillproof.model.request.experience.UpdateExperienceRequest;

import java.util.List;

public interface ExperienceService {
    ExperienceResponse createExperience(CreateExperienceRequest createExperienceRequest);

    List<ExperienceResponse> getExperienceByUserId(String userId);

    ExperienceResponse getExperienceById(Long id);

    List<ExperienceResponse> listAllExperiences();

    ExperienceResponse updateExperience(Long id, UpdateExperienceRequest updateExperienceRequest);

    void deleteExperienceById(Long id);
}
