package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.FraudAlertDTO;
import com.wipro.FinGenieAI.entity.FraudAlert;

public class FraudAlertMapper {

	public static FraudAlertDTO toDTO(FraudAlert alert) {
	    if (alert == null) return null;

	    return FraudAlertDTO.builder()
	            .id(alert.getId())
	            .message(alert.getMessage())
	            .riskLevel(alert.getRiskLevel())
	            .riskScore(alert.getRiskScore())
	            .flagged(alert.isFlagged()) 
	            .transactionId(
	                    alert.getTransaction() != null 
	                    ? alert.getTransaction().getId() 
	                    : null
	            ) 
	            .createdAt(alert.getCreatedAt()) 
	            .build();
	}

	public static FraudAlert toEntity(FraudAlertDTO dto) {
	    if (dto == null) return null;

	    return FraudAlert.builder()
	            .id(dto.getId())
	            .message(dto.getMessage())
	            .riskLevel(dto.getRiskLevel())
	            .riskScore(dto.getRiskScore())
	            .flagged(dto.isFlagged()) 
	            .build();
	}
}