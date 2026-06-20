package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.LoanDTO;
import com.wipro.FinGenieAI.entity.Loan;

public class LoanMapper {

    public static LoanDTO toDTO(Loan loan) {
        if (loan == null) return null;

        return LoanDTO.builder()
                .id(loan.getId())
                .accountId(loan.getAccount().getId()) 
                .amount(loan.getAmount())
                .interestRate(loan.getInterestRate())
                .tenure(loan.getTenure())
                .status(loan.getStatus())
                .loanType(loan.getLoanType()) 
                .build();
    }

    public static Loan toEntity(LoanDTO dto) {
        if (dto == null) return null;

        return Loan.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .interestRate(dto.getInterestRate())
                .tenure(dto.getTenure())
                .loanType(dto.getLoanType()) 
                
                .build();
    }
}