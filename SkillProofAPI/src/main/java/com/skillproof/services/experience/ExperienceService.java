package com.skillproof.services.experience;

import com.skillproof.model.request.experience.CreateExperienceRequest;
import com.skillproof.model.request.experience.ExperienceResponse;

import java.util.List;

public interface ExperienceService {
    ExperienceResponse createExperience(CreateExperienceRequest createExperienceRequest);

    List<ExperienceResponse> getExperienceByUserId(String userId);
}
