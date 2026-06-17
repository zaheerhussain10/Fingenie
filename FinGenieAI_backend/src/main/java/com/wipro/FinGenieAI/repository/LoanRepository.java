package com.wipro.FinGenieAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wipro.FinGenieAI.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}