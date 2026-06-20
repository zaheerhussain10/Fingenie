package com.wipro.FinGenieAI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.FinGenieAI.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId); 

    Account findByAccountNumber(String accountNumber); 
}