package com.skillproof.repositories;

import com.skillproof.model.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillDao extends JpaRepository<Skill, Long> {

    List<Skill> findByUserId(String userId);
}
