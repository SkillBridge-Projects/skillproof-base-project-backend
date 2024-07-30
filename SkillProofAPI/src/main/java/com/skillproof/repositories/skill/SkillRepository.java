package com.skillproof.repositories.skill;

import com.skillproof.model.entity.Skill;
import com.skillproof.model.request.skill.SkillResponse;

import java.util.List;

public interface SkillRepository {
    Skill getSkillById(Long id);

    List<Skill> getSkillsByUserId(String userId);

    List<Skill> listAllSkills();

    Skill updateSkill(Skill skill);

    void deleteSkillById(Long id);

    Skill createSkill(Skill skill);
}
