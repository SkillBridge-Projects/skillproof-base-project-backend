package com.skillproof.repositories;

import com.skillproof.model.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationDao extends JpaRepository<Education, Long> {

    List<Education> findByUserId(String userId);
}
