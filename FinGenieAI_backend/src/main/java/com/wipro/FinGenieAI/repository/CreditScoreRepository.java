package com.wipro.FinGenieAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.FinGenieAI.entity.CreditScore;

public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

}
