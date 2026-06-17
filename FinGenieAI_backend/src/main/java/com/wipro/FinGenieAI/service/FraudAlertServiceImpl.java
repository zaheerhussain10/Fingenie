package com.wipro.FinGenieAI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.FraudAlertDTO;
import com.wipro.FinGenieAI.entity.FraudAlert;
import com.wipro.FinGenieAI.entity.Transaction;
import com.wipro.FinGenieAI.enums.RiskLevel;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.mapper.FraudAlertMapper;
import com.wipro.FinGenieAI.repository.FraudAlertRepository;
import com.wipro.FinGenieAI.repository.TransactionRepository;

@Service
public class FraudAlertServiceImpl implements FraudAlertService {

    @Autowired
    private FraudAlertRepository fraudAlertRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public FraudAlertDTO checkFraud(Long transactionId) {

        // ✅ 1. Get transaction
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        double amount = transaction.getAmount();

        RiskLevel riskLevel;
        double riskScore;
        boolean flagged;
        String message;

        // ✅ 2. Fraud logic 
        if (amount > 50000) {
            riskLevel = RiskLevel.HIGH;
            riskScore = 0.9;
            flagged = true;
            message = "High value transaction detected";
        } else if (amount > 20000) {
            riskLevel = RiskLevel.MEDIUM;
            riskScore = 0.6;
            flagged = true;
            message = "Moderate risk transaction";
        } else {
            riskLevel = RiskLevel.LOW;
            riskScore = 0.2;
            flagged = false;
            message = "Normal transaction";
        }

        // ✅ 3. Create FraudAlert
        FraudAlert alert = FraudAlert.builder()
                .message(message)
                .riskLevel(riskLevel)
                .riskScore(riskScore)
                .flagged(flagged)
                .transaction(transaction)
                .build();

        FraudAlert saved = fraudAlertRepository.save(alert);

        return FraudAlertMapper.toDTO(saved);
    }
}