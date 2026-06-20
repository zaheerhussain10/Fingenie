package com.wipro.FinGenieAI.dto;

import java.time.LocalDateTime;

import com.wipro.FinGenieAI.enums.RiskLevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudAlertDTO {

    private Long id;

    private String message;

    private RiskLevel riskLevel;

    private Double riskScore;

    private boolean flagged;

    private Long transactionId;

    private LocalDateTime createdAt;
}
