package com.wipro.FinGenieAI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.FinGenieAI.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ Get all transactions of an account
    List<Transaction> findByAccountId(Long accountId);

}