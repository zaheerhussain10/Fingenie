package com.wipro.FinGenieAI.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.LoanDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Loan;
import com.wipro.FinGenieAI.enums.LoanStatus;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.mapper.LoanMapper;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger logger =
            LoggerFactory.getLogger(LoanServiceImpl.class);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    // ✅ APPLY LOAN
    @Override
    public LoanDTO applyLoan(LoanDTO dto) {

        // ✅ 1. Get account
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // ✅ 2. Map
        Loan loan = LoanMapper.toEntity(dto);
        loan.setAccount(account);
        loan.setAppliedDate(LocalDateTime.now());

        // ✅ 3. Set interest & tenure (🔥 HERE)
        switch (dto.getLoanType()) {
            case HOME_LOAN:
                loan.setInterestRate(7.2);
                loan.setTenure(240);
                break;

            case CAR_LOAN:
                loan.setInterestRate(8.5);
                loan.setTenure(60);
                break;

            case PERSONAL_LOAN:
                loan.setInterestRate(10.5);
                loan.setTenure(36);
                break;

            case EDUCATION_LOAN:
                loan.setInterestRate(6.8);
                loan.setTenure(120);
                break;
        }

        // ✅ 4. Eligibility check
        if (account.getBalance() >= (dto.getAmount() * 0.2)) {
            loan.setStatus(LoanStatus.APPROVED);
            loan.setApprovedDate(LocalDateTime.now());
        } else {
            loan.setStatus(LoanStatus.REJECTED);
        }

        // ✅ 5. Save
        Loan saved = loanRepository.save(loan);

        return LoanMapper.toDTO(saved);
    }


    // ✅ GET LOAN BY ID
    @Override
    public LoanDTO getLoanById(Long id) {

        logger.info("Fetching loan with id: {}", id);

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Loan not found with id: {}", id);
                    return new ResourceNotFoundException("Loan not found");
                });

        return LoanMapper.toDTO(loan);
    }
}