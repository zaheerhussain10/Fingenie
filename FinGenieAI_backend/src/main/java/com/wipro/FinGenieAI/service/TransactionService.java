package com.wipro.FinGenieAI.service;
import java.util.List;


import com.wipro.FinGenieAI.dto.TransactionDTO;

public interface TransactionService {

    TransactionDTO deposit(Long accountId, Double amount);

    TransactionDTO withdraw(Long accountId, Double amount);

    List<TransactionDTO> getTransactionsByAccountId(Long accountId);

    TransactionDTO transfer(Long fromAccountId, Long toAccountId, Double amount);
}