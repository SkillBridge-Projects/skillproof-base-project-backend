package com.skillproof.repositories.portfolio;

import com.skillproof.model.entity.Portfolio;
import com.skillproof.repositories.PortfolioDao;
import org.springframework.stereotype.Repository;

@Repository
public class PortfolioRepositoryImpl implements PortfolioRepository {

    private final PortfolioDao portfolioDao;

    public PortfolioRepositoryImpl(PortfolioDao portfolioDao) {
        this.portfolioDao = portfolioDao;
    }

    @Override
    public void addPortfolioVideo(Portfolio portfolio) {
        portfolioDao.saveAndFlush(portfolio);
    }

    @Override
    public Portfolio getPortfolioByUserId(String userId) {
        return portfolioDao.findByUserId(userId);
    }

    @Override
    public Portfolio getPortfolioById(Long id) {
        return portfolioDao.findById(id).orElse(null);
    }

    @Override
    public Portfolio updatePortfolio(Portfolio portfolio) {
        return portfolioDao.saveAndFlush(portfolio);
    }
}
