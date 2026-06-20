package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.CreditScoreDTO;
import com.wipro.FinGenieAI.entity.CreditScore;

public class CreditScoreMapper {

	public static CreditScoreDTO toDTO(CreditScore cs) {
	    if (cs == null) return null;

	    return CreditScoreDTO.builder()
	            .id(cs.getId())
	            .userId(cs.getUserId()) 
	            .score(cs.getScore())
	            .rating(cs.getRating())
	            .calculatedAt(cs.getCalculatedAt()) 
	            .build();
	}

	public static CreditScore toEntity(CreditScoreDTO dto) {
	    if (dto == null) return null;

	    return CreditScore.builder()
	            .id(dto.getId())
	            .userId(dto.getUserId())
	            .score(dto.getScore())
	            .rating(dto.getRating())
	            .build();
	}
}
