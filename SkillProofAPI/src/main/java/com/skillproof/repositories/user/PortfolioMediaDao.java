package com.skillproof.repositories.user;

import com.skillproof.model.entity.PortfolioMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioMediaDao extends JpaRepository<PortfolioMedia, Long> {

    List<PortfolioMedia> findAllByPortfolioId(Long id);
}

