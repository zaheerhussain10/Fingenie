package com.wipro.FinGenieAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.CreditScoreDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.CreditScore;
import com.wipro.FinGenieAI.entity.Loan;
import com.wipro.FinGenieAI.entity.Transaction;
import com.wipro.FinGenieAI.enums.LoanStatus;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.mapper.CreditScoreMapper;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.CreditScoreRepository;
import com.wipro.FinGenieAI.repository.LoanRepository;
import com.wipro.FinGenieAI.repository.TransactionRepository;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CreditScoreRepository creditScoreRepository;

    @Override
    public CreditScoreDTO calculateScore(Long userId) {

        // ✅ 1. Get user account
        List<Account> accounts = accountRepository.findByUserId(userId);

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("No account found for user");
        }

        double totalBalance = 0;
        int totalTransactions = 0;

        // ✅ 2. Loop accounts
        for (Account acc : accounts) {

            totalBalance += acc.getBalance();

            List<Transaction> txs = transactionRepository.findByAccountId(acc.getId());
            totalTransactions += txs.size();
        }

        int score = 300; // base score

        // ✅ 3. Balance logic
        if (totalBalance > 50000) {
            score += 300;
        } else if (totalBalance > 20000) {
            score += 200;
        } else {
            score += 100;
        }

        // ✅ 4. Transaction activity
        if (totalTransactions > 10) {
            score += 200;
        } else {
            score += 100;
        }

        // ✅ 5. Loan behavior
        List<Loan> loans = loanRepository.findAll();

        for (Loan loan : loans) {
            if (loan.getAccount() != null &&
                loan.getAccount().getUserId().equals(userId) &&
                loan.getStatus() == LoanStatus.APPROVED) {

                score += 200;
                break;
            }
        }

        // ✅ LIMIT SCORE
        if (score > 900) {
            score = 900;
        }

        // ✅ 6. SET RATING
        String rating;

        if (score >= 800) {
            rating = "EXCELLENT";
        } else if (score >= 700) {
            rating = "GOOD";
        } else if (score >= 600) {
            rating = "AVERAGE";
        } else {
            rating = "POOR";
        }

        // ✅ 7. SAVE CREDIT SCORE
        CreditScore cs = CreditScore.builder()
                .userId(userId)
                .score(score)
                .rating(rating)
                .build();

        CreditScore saved = creditScoreRepository.save(cs);

        return CreditScoreMapper.toDTO(saved);
    }
}