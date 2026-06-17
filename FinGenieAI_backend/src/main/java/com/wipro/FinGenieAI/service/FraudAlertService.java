package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.FraudAlertDTO;

public interface FraudAlertService {

    FraudAlertDTO checkFraud(Long transactionId);
}