package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.SkillsAndExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillsAndExperienceDao extends JpaRepository<SkillsAndExperience, Long> {

    List<SkillsAndExperience> findByUserId(Long userId);
}
