package com.skillproof.skillproofapi.services.experience;

import com.skillproof.skillproofapi.model.request.experience.CreateExperienceRequest;
import com.skillproof.skillproofapi.model.request.experience.ExperienceResponse;

import java.util.List;

public interface ExperienceService {
    ExperienceResponse createExperience(CreateExperienceRequest createExperienceRequest);

    List<ExperienceResponse> getExperienceByUserId(String userId);
}
