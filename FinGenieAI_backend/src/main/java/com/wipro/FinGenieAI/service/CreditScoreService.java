package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.CreditScoreDTO;

public interface CreditScoreService {

    CreditScoreDTO calculateScore(Long userId);
}
