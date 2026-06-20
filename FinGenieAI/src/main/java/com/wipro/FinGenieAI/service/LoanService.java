package com.wipro.FinGenieAI.service;

import java.util.List;

import com.wipro.FinGenieAI.dto.LoanDTO;

public interface LoanService {

    LoanDTO applyLoan(LoanDTO dto);

    LoanDTO getLoanById(Long id);

    void updateStatus(Long id, String status);

    List<LoanDTO> getAllLoans();

}