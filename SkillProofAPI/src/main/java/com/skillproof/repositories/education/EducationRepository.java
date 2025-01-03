package com.skillproof.repositories.education;

import com.skillproof.model.entity.Education;

import java.util.List;

public interface EducationRepository {

    Education createEducation(Education education);

    List<Education> getEducationByUserId(String userId);

    List<Education> getEducationByDetailsUserId(String userId);
}
