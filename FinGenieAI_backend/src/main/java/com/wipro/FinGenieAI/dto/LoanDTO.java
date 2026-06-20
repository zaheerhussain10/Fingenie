package com.wipro.FinGenieAI.dto;

import com.wipro.FinGenieAI.enums.LoanStatus;
import com.wipro.FinGenieAI.enums.LoanType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    private Integer tenure;

    private LoanStatus status;

    @NotNull(message = "Loan type is required")
    private LoanType loanType;
}
