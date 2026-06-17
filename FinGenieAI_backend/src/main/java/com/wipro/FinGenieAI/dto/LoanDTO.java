package com.wipro.FinGenieAI.dto;

import com.wipro.FinGenieAI.enums.LoanStatus;
import com.wipro.FinGenieAI.enums.LoanType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDTO {

    private Long id;

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Loan amount is required")
    @Min(value = 1000, message = "Minimum loan amount is 1000")
    private Double amount;

    private Double interestRate;

    @Min(value = 1, message = "Tenure must be at least 1")
    private Integer tenure;

    private LoanStatus status; // system controlled

    @NotNull(message = "Loan type is required")
    private LoanType loanType;
}