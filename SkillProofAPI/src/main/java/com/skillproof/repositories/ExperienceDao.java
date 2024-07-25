package com.skillproof.repositories;

import com.skillproof.model.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceDao extends JpaRepository<Experience, Long> {

    List<Experience> findByUserId(String userId);
}
