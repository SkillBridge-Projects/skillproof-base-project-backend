package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceDao extends JpaRepository<Experience, Long> {

    List<Experience> findByUserId(String userId);
}
