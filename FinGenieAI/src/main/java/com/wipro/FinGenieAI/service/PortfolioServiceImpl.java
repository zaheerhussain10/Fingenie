package com.wipro.FinGenieAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.PortfolioDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Investment;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.InvestmentRepository;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private InvestmentRepository investmentRepository;

    @Override
    public PortfolioDTO getPortfolio(Long accountId) {

        // ✅ 1. Validate account exists
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // ✅ 2. Get investments
        List<Investment> investments = investmentRepository.findByAccountId(accountId);

        // ✅ 3. Calculate values
        double totalInvestment = 0;
        double totalReturns = 0;

        for (Investment inv : investments) {
            totalInvestment += inv.getAmount();
            totalReturns += inv.getReturns();
        }

        // ✅ 4. Build DTO (NO MAPPER needed)
        return PortfolioDTO.builder()
                .accountId(accountId)
                .totalInvestment(totalInvestment)
                .totalReturns(totalReturns)
                .numberOfInvestments(investments.size())
                .currentBalance(account.getBalance())
                .build();
    }
}