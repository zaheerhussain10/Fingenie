package com.wipro.FinGenieAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.FinGenieAI.dto.PortfolioDTO;
import com.wipro.FinGenieAI.service.PortfolioService;
@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    // ✅ GET PORTFOLIO (Dashboard API)
    @GetMapping("/{accountId}")
    public PortfolioDTO getPortfolio(@PathVariable Long accountId) {
        return portfolioService.getPortfolio(accountId);
    }
}