package com.skillproof.skillproofapi.repositories.skillsAndExperience;

import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;
import com.skillproof.skillproofapi.repositories.SkillsAndExperienceDao;
import com.skillproof.skillproofapi.services.skillAndExperience.SkillsAndExperienceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillsAndExperienceRepositoryImpl implements SkillsAndExperienceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SkillsAndExperienceRepositoryImpl.class);

    private final SkillsAndExperienceDao skillsAndExperienceDao;

    public SkillsAndExperienceRepositoryImpl(SkillsAndExperienceDao skillsAndExperienceDao){
        this.skillsAndExperienceDao = skillsAndExperienceDao;
    }


    @Override
    public List<SkillsAndExperience> getSkillsAndExperiencesByUserId(Long userId) {
        LOG.info("Start of getSkillsAndExperiencesByUserId method.");
        return skillsAndExperienceDao.findByUserId(userId);
    }

    @Override
    public SkillsAndExperience createSkillsAndExperience(SkillsAndExperience skillsAndExperience) {
        LOG.info("Start of createSkillsAndExperience method.");
        return skillsAndExperienceDao.saveAndFlush(skillsAndExperience);
    }
}
