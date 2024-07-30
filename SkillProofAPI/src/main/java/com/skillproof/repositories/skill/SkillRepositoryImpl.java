package com.skillproof.repositories.skill;

import com.skillproof.model.entity.Skill;
import com.skillproof.repositories.SkillDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillRepositoryImpl implements SkillRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SkillRepositoryImpl.class);

    private final SkillDao skillDao;

    public SkillRepositoryImpl(SkillDao skillDao) {
        this.skillDao = skillDao;
    }

    @Override
    public Skill createSkill(Skill skill) {
        LOG.debug("Start of createSkill method.");
        return skillDao.saveAndFlush(skill);
    }

    @Override
    public Skill getSkillById(Long id) {
        LOG.debug("Start of getSkillById method.");
        return skillDao.findById(id).orElse(null);
    }

    @Override
    public List<Skill> getSkillsByUserId(String userId) {
        LOG.debug("Start of getSkillsByUserId method.");
        return skillDao.findByUserId(userId);
    }

    @Override
    public List<Skill> listAllSkills() {
        LOG.debug("Start of listAllSkills method.");
        return skillDao.findAll();
    }

    @Override
    public Skill updateSkill(Skill skill) {
        LOG.debug("Start of updateSkill method.");
        return skillDao.saveAndFlush(skill);
    }

    @Override
    public void deleteSkillById(Long id) {
        LOG.debug("Start of deleteSkillById method.");
        skillDao.deleteById(id);
    }
}
