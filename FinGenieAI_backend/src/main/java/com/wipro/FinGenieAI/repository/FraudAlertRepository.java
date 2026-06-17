package com.wipro.FinGenieAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.FinGenieAI.entity.FraudAlert;

public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {
}