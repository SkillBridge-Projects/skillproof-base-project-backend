package com.skillproof.repositories.portfolio;

import com.skillproof.model.entity.Portfolio;
import com.skillproof.model.entity.PortfolioMedia;
import com.skillproof.repositories.PortfolioDao;
import com.skillproof.repositories.user.PortfolioMediaDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PortfolioRepositoryImpl implements PortfolioRepository {

    private final PortfolioDao portfolioDao;
    private final PortfolioMediaDao portfolioMediaDao;

    public PortfolioRepositoryImpl(PortfolioDao portfolioDao, PortfolioMediaDao portfolioMediaDao) {
        this.portfolioDao = portfolioDao;
        this.portfolioMediaDao = portfolioMediaDao;
    }

    @Override
    public Portfolio addPortfolioVideo(Portfolio portfolio) {
        return portfolioDao.saveAndFlush(portfolio);
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

    @Override
    public List<PortfolioMedia> addPortfolioMedia(List<PortfolioMedia> mediaList) {
        return portfolioMediaDao.saveAllAndFlush(mediaList);
    }
}
