package com.skillproof.repositories.portfolio;

import com.skillproof.model.entity.Portfolio;

public interface PortfolioRepository {

    void addPortfolioVideo(Portfolio portfolio);

    Portfolio getPortfolioByUserId(String userId);

    Portfolio getPortfolioById(Long id);

    Portfolio updatePortfolio(Portfolio portfolio);
}
