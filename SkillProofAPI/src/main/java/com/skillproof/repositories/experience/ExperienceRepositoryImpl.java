package com.skillproof.repositories.experience;

import com.skillproof.model.entity.Experience;
import com.skillproof.repositories.ExperienceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExperienceRepositoryImpl implements ExperienceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ExperienceRepositoryImpl.class);

    private final ExperienceDao experienceDao;

    public ExperienceRepositoryImpl(ExperienceDao experienceDao){
        this.experienceDao = experienceDao;
    }


    @Override
    public List<Experience> getExperienceByUserId(String userId) {
        LOG.debug("Start of getExperienceByUserId method.");
        return experienceDao.findByUserId(userId);
    }


    @Override
    public Experience createExperience(Experience Experience) {
        LOG.debug("Start of createSkillsAndExperience method.");
        return experienceDao.saveAndFlush(Experience);
    }

    @Override
    public List<Experience> listAllExperienceDetails() {
        LOG.debug("Start of listAllExperienceDetails method.");
        return experienceDao.findAll();
    }

    @Override
    public Experience getExperienceById(Long id) {
        LOG.debug("Start of getExperienceById method.");
        return experienceDao.findById(id).orElse(null);
    }

    @Override
    public Experience updateExperience(Experience experience) {
        LOG.debug("Start of updateExperience method.");
        return experienceDao.saveAndFlush(experience);
    }

    @Override
    public void deleteExperienceById(Long id) {
        LOG.debug("Start of deleteExperienceById method.");
        experienceDao.deleteById(id);
    }
}
