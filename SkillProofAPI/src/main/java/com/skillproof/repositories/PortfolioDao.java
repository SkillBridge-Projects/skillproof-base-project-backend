package com.skillproof.repositories;

import com.skillproof.model.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioDao extends JpaRepository<Portfolio, Long> {

    Portfolio findByUserId(String userId);
}

