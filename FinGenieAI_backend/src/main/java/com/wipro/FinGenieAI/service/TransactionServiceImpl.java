package com.wipro.FinGenieAI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.TransactionDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Transaction;
import com.wipro.FinGenieAI.enums.TransactionStatus;
import com.wipro.FinGenieAI.enums.TransactionType;
import com.wipro.FinGenieAI.exception.InsufficientBalanceException;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.mapper.TransactionMapper;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    // ✅ DEPOSIT
    @Override
    public TransactionDTO deposit(Long accountId, Double amount) {

        Account account = accountRepository.findById(accountId)
        		.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // ✅ Update balance
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // ✅ Create transaction
        Transaction tx = Transaction.builder()
                .account(account)
                .amount(amount)
                .type(TransactionType.DEBIT)
                .status(TransactionStatus.SUCCESS)
                .build();

        return TransactionMapper.toDTO(transactionRepository.save(tx));
    }

    // ✅ WITHDRAW
    @Override
    public TransactionDTO withdraw(Long accountId, Double amount) {

        Account account = accountRepository.findById(accountId)
        		.orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // ✅ Rule: sufficient balance
        if (account.getBalance() < amount) {
        	throw new InsufficientBalanceException("Insufficient balance"); 
        }

        // ✅ Rule: minimum balance = 1000
        if ((account.getBalance() - amount) < 1000) {
            throw new RuntimeException("Minimum balance must be 1000");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction tx = Transaction.builder()
                .account(account)
                .amount(amount)
                .type(TransactionType.CREDIT)
                .status(TransactionStatus.SUCCESS)
                .build();

        return TransactionMapper.toDTO(transactionRepository.save(tx));
    }

    // ✅ TRANSFER
    @Override
    @Transactional
    public TransactionDTO transfer(Long fromAccountId, Long toAccountId, Double amount) {

        Account sender = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // ✅ Rule: sufficient balance
        if (sender.getBalance() < amount) {
        	throw new InsufficientBalanceException("Insufficient balance");
        }

        // ✅ Deduct from sender
        sender.setBalance(sender.getBalance() - amount);

        // ✅ Add to receiver
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction tx = Transaction.builder()
                .account(sender)
                .amount(amount)
                .type(TransactionType.DEBIT)
                .status(TransactionStatus.SUCCESS)
                .toAccountId(toAccountId)
                .build();

        return TransactionMapper.toDTO(transactionRepository.save(tx));
    }
    @Override
    public List<TransactionDTO> getTransactionsByAccountId(Long accountId) {

        // ✅ check account exists
        accountRepository.findById(accountId)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(TransactionMapper::toDTO)
                .toList();
    }
}
