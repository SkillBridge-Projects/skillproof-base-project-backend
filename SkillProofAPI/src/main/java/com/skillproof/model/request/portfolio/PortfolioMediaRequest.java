package com.skillproof.model.request.portfolio;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioMediaRequest {

    List<CreatePortfolioMediaRequest> portfolioMediaRequests;
}
