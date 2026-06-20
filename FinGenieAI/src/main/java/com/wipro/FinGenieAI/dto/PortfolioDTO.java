package com.wipro.FinGenieAI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioDTO {

    private Long accountId;          // ✅ which account this portfolio belongs to

    private Double totalInvestment;  // ✅ total invested amount

    private Double totalReturns;     // ✅ expected returns

    private Integer numberOfInvestments; // ✅ count of investments

    private Double currentBalance;   // ✅ remaining balance in account
}