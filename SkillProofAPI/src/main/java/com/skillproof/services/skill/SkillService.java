package com.skillproof.services.skill;

import com.skillproof.model.request.skill.CreateSkillRequest;
import com.skillproof.model.request.skill.SkillResponse;
import com.skillproof.model.request.skill.UpdateSkillRequest;

import java.util.List;

public interface SkillService {
    SkillResponse createSkill(CreateSkillRequest createSkillRequest);

    List<SkillResponse> getSkillsByUserId(String userId);

    SkillResponse getSkillById(Long id);

    List<SkillResponse> listAllSkills();

    SkillResponse updateSkill(Long id, UpdateSkillRequest updateSkillRequest);

    void deleteSkillById(Long id);
}
