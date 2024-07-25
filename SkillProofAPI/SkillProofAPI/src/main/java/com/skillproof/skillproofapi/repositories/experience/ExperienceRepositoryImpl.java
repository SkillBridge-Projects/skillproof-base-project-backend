package com.skillproof.skillproofapi.repositories.experience;

import com.skillproof.skillproofapi.model.entity.Experience;
import com.skillproof.skillproofapi.repositories.ExperienceDao;
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
}
