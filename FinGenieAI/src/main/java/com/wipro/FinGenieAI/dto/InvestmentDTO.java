package com.wipro.FinGenieAI.dto;

import com.wipro.FinGenieAI.enums.InvestmentType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestmentDTO {

    private Long id;

    @NotNull(message = "Account ID is required")
    private Long accountId; 

    @NotNull(message = "Investment type is required")
    private InvestmentType type;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotNull(message = "Returns are required")
    private Double returns;
}