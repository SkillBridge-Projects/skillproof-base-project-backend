package com.skillproof.services.education;

import com.skillproof.model.request.education.CreateEducationRequest;
import com.skillproof.model.request.education.EducationResponse;
import com.skillproof.model.request.education.UpdateEducationRequest;

import java.util.List;

public interface EducationService {

    EducationResponse createEducation(CreateEducationRequest createEducationRequest);

    List<EducationResponse> getEducationByUserId(String userId);

    List<EducationResponse> listAllEducationDetails();

    EducationResponse updateEducation(Long id, UpdateEducationRequest updateEducationRequest);

    void deleteEducationById(Long id);

    EducationResponse getEducationById(Long id);
}
