package com.skillproof.model.request.portfolio;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortFolioMediaRequest {

    List<CreatePortfolioMediaRequest> portfolioMediaRequests;
}
