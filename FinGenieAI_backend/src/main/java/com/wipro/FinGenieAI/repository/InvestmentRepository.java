package com.wipro.FinGenieAI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.FinGenieAI.entity.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {

    List<Investment> findByAccountId(Long accountId);
}
