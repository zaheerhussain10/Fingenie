package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.InvestmentDTO;
import com.wipro.FinGenieAI.entity.Investment;

public class InvestmentMapper {

	public static InvestmentDTO toDTO(Investment investment) {
	    if (investment == null) return null;

	    return InvestmentDTO.builder()
	            .id(investment.getId())
	            .accountId(investment.getAccount() != null ? investment.getAccount().getId() : null) 
	            .type(investment.getType())
	            .amount(investment.getAmount())
	            .returns(investment.getReturns())
	            .build();
	}


    public static Investment toEntity(InvestmentDTO dto) {
        if (dto == null) return null;

        return Investment.builder()
                .id(dto.getId())
                .type(dto.getType())
                .amount(dto.getAmount())
                .returns(dto.getReturns())
                .build();
    }
}
