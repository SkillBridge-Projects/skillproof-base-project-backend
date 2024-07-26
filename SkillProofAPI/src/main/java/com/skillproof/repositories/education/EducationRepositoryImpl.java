package com.skillproof.repositories.education;

import com.skillproof.model.entity.Education;
import com.skillproof.repositories.EducationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EducationRepositoryImpl implements EducationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(EducationRepositoryImpl.class);

    private final EducationDao educationDao;

    public EducationRepositoryImpl(EducationDao educationDao) {
        this.educationDao = educationDao;
    }

    @Override
    public Education createEducation(Education education) {
        LOG.debug("Start of createEducation method.");
        return educationDao.saveAndFlush(education);
    }

    @Override
    public List<Education> getEducationByUserId(String userId) {
        LOG.debug("Start of getEducationByUserId method.");
        return educationDao.findByUserId(userId);
    }

    @Override
    public List<Education> listAllEducationDetails() {
        LOG.debug("Start of listAllEducationDetails method.");
        return educationDao.findAll();
    }

    @Override
    public Education getEducationById(Long id) {
        LOG.debug("Start of getEducationById method.");
        return educationDao.findById(id).orElse(null);
    }

    @Override
    public Education updateEducation(Education education) {
        LOG.debug("Start of updateEducation method.");
        return educationDao.saveAndFlush(education);
    }

    @Override
    public void deleteEducationById(Long id) {
        LOG.debug("Start of deleteEducationById method.");
        educationDao.deleteById(id);
    }
}
