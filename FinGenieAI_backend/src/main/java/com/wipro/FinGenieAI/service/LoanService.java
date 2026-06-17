package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.LoanDTO;

public interface LoanService {

    LoanDTO applyLoan(LoanDTO dto);

    LoanDTO getLoanById(Long id);
}