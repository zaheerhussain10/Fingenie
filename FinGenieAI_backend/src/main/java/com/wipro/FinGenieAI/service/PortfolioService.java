package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.PortfolioDTO;

public interface PortfolioService {

    PortfolioDTO getPortfolio(Long accountId);
}