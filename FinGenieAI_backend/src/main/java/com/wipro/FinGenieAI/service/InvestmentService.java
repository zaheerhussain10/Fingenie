package com.wipro.FinGenieAI.service;

import java.util.List;
import com.wipro.FinGenieAI.dto.InvestmentDTO;

public interface InvestmentService {

    InvestmentDTO createInvestment(InvestmentDTO dto);

    List<InvestmentDTO> getInvestmentsByAccount(Long accountId);
}