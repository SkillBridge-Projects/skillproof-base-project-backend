package com.skillproof.repositories.portfolio;

import com.skillproof.model.entity.Portfolio;
import com.skillproof.model.entity.PortfolioMedia;

import java.util.List;

public interface PortfolioRepository {

    Portfolio addPortfolioVideo(Portfolio portfolio);

    Portfolio getPortfolioByUserId(String userId);

    Portfolio getPortfolioById(Long id);

    Portfolio updatePortfolio(Portfolio portfolio);

    List<PortfolioMedia> addPortfolioMedia(List<PortfolioMedia> mediaList);
}
