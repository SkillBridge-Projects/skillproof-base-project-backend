package com.skillproof.services.education;

import com.skillproof.model.request.education.CreateEducationRequest;
import com.skillproof.model.request.education.EducationResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface EducationService {

    EducationResponse createEducation(CreateEducationRequest createEducationRequest);

    List<EducationResponse> getEducationByUserId(String userId);

    EducationResponse getEducationDetailsByUserId(String userId) throws AccessDeniedException;
}
