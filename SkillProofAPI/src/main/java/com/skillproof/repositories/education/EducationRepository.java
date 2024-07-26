package com.skillproof.repositories.education;

import com.skillproof.model.entity.Education;

import java.util.List;

public interface EducationRepository {

    Education createEducation(Education education);

    List<Education> getEducationByUserId(String userId);

    List<Education> listAllEducationDetails();

    Education getEducationById(Long id);

    Education updateEducation(Education education);

    void deleteEducationById(Long id);
}
