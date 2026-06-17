package com.wipro.FinGenieAI.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.InvestmentDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Investment;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.mapper.InvestmentMapper;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.InvestmentRepository;

import jakarta.transaction.Transactional;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(InvestmentServiceImpl.class);

    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private AccountRepository accountRepository;

    // ✅ CREATE INVESTMENT
    @Override
    @Transactional
    public InvestmentDTO createInvestment(InvestmentDTO dto) {

        logger.info("Creating investment for accountId: {}", dto.getAccountId());

        // ✅ 1. Validate account
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // ✅ 2. Check balance
        if (account.getBalance() < dto.getAmount()) {
            logger.error("Insufficient balance for accountId: {}", dto.getAccountId());
            throw new IllegalArgumentException("Insufficient balance for investment");
        }

        // ✅ 3. Deduct amount
        account.setBalance(account.getBalance() - dto.getAmount());
        accountRepository.save(account);

        // ✅ 4. Create investment
        Investment investment = InvestmentMapper.toEntity(dto);
        investment.setAccount(account);

        Investment saved = investmentRepository.save(investment);

        logger.info("Investment created successfully with id: {}", saved.getId());

        return InvestmentMapper.toDTO(saved);
    }

    // ✅ GET INVESTMENTS
    @Override
    public List<InvestmentDTO> getInvestmentsByAccount(Long accountId) {

        logger.info("Fetching investments for accountId: {}", accountId);

        accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return investmentRepository.findByAccountId(accountId)
                .stream()
                .map(InvestmentMapper::toDTO)
                .collect(Collectors.toList());
    }
}