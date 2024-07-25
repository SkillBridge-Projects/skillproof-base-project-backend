package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobsRepository extends JpaRepository<Job, Long> {
}
