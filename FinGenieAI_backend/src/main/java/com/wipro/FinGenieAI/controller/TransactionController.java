package com.wipro.FinGenieAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.FinGenieAI.dto.TransactionDTO;
import com.wipro.FinGenieAI.service.TransactionService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // ✅ DEPOSIT
    @PostMapping("/deposit")
    public TransactionDTO deposit( @RequestBody TransactionDTO dto) {
    	System.out.println("AccountId: " + dto.getAccountId());
    	System.out.println("Amount: " + dto.getAmount());

        return transactionService.deposit(
                dto.getAccountId(),
                dto.getAmount()
        );
    }

    // ✅ WITHDRAW
    @PostMapping("/withdraw")
    public TransactionDTO withdraw(@RequestBody TransactionDTO dto) {

        return transactionService.withdraw(
                dto.getAccountId(),
                dto.getAmount()
        );
    }

    // ✅ TRANSFER
    @PostMapping("/transfer")
    public TransactionDTO transfer(@RequestBody TransactionDTO dto) {

        return transactionService.transfer(
                dto.getFromAccountId(),
                dto.getToAccountId(),
                dto.getAmount()
        );
    }

    // ✅ GET TRANSACTIONS
    @GetMapping("/account/{accountId}")
    public List<TransactionDTO> getTransactions(@PathVariable Long accountId) {
        return transactionService.getTransactionsByAccountId(accountId);
    }
}