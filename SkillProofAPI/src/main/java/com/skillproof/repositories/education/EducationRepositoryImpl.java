package com.skillproof.repositories.education;

import com.skillproof.model.entity.Education;
import com.skillproof.repositories.EducationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EducationRepositoryImpl implements EducationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(EducationRepositoryImpl.class);

    private final EducationDAO educationDAO;

    public EducationRepositoryImpl(EducationDAO educationDAO) {
        this.educationDAO = educationDAO;
    }

    @Override
    public Education createEducation(Education education) {
        LOG.debug("Start of createEducation method - EducationRepositoryImpl");
        return educationDAO.saveAndFlush(education);
    }

    @Override
    public List<Education> getEducationByUserId(String userId) {
        LOG.debug("Start of getEducationByUserId method - EducationRepositoryImpl");
        return educationDAO.findByUserId(userId);
    }

    @Override
    public List<Education> getEducationByDetailsUserId(String userId) {
        return educationDAO.findByUserId(userId);
    }
}
