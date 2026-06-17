package com.wipro.FinGenieAI.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditScoreDTO {

    private Long id;

    private Long userId; 

    private Integer score;

    private String rating;

    private LocalDateTime calculatedAt; 
}