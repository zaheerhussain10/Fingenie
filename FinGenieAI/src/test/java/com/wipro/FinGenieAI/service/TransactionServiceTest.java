package com.wipro.FinGenieAI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.FinGenieAI.dto.TransactionDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Transaction;
import com.wipro.FinGenieAI.exception.InsufficientBalanceException;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.TransactionRepository;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ DEPOSIT SUCCESS
    @Test
    void testDeposit_Success() {

        Account account = new Account();
        account.setId(1L);
        account.setBalance(5000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TransactionDTO result = transactionService.deposit(1L, 2000.0);

        assertNotNull(result);

        // ✅ balance updated
        assertEquals(7000.0, account.getBalance());

        verify(accountRepository, times(1)).save(account);
    }

    // ✅ ACCOUNT NOT FOUND (DEPOSIT)
    @Test
    void testDeposit_AccountNotFound() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.deposit(1L, 1000.0);
        });
    }

    // ✅ WITHDRAW SUCCESS
    @Test
    void testWithdraw_Success() {

        Account account = new Account();
        account.setId(1L);
        account.setBalance(5000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TransactionDTO result = transactionService.withdraw(1L, 2000.0);

        assertNotNull(result);

        // ✅ balance reduced
        assertEquals(3000.0, account.getBalance());
    }

    // ✅ INSUFFICIENT BALANCE
    @Test
    void testWithdraw_InsufficientBalance() {

        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.withdraw(1L, 2000.0);
        });
    }

    // ✅ MINIMUM BALANCE RULE
    @Test
    void testWithdraw_MinimumBalanceViolation() {

        Account account = new Account();
        account.setId(1L);
        account.setBalance(1500.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class, () -> {
            transactionService.withdraw(1L, 600.0); // leaves < 1000
        });
    }

    // ✅ TRANSFER SUCCESS
    @Test
    void testTransfer_Success() {

        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(10000.0);

        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(2000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(receiver));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TransactionDTO result = transactionService.transfer(1L, 2L, 3000.0);

        assertNotNull(result);

        // ✅ balances updated
        assertEquals(7000.0, sender.getBalance());
        assertEquals(5000.0, receiver.getBalance());
    }

    // ✅ TRANSFER INSUFFICIENT BALANCE
    @Test
    void testTransfer_InsufficientBalance() {

        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(1000.0);

        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(2000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(sender));

        when(accountRepository.findById(2L))
                .thenReturn(Optional.of(receiver));

        assertThrows(InsufficientBalanceException.class, () -> {
            transactionService.transfer(1L, 2L, 5000.0);
        });
    }

    // ✅ GET TRANSACTIONS
    @Test
    void testGetTransactionsByAccountId() {

        Account account = new Account();
        account.setId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        Transaction tx1 = new Transaction();
        Transaction tx2 = new Transaction();

        when(transactionRepository.findByAccountId(1L))
                .thenReturn(List.of(tx1, tx2));

        List<TransactionDTO> result =
                transactionService.getTransactionsByAccountId(1L);

        assertEquals(2, result.size());
    }

    // ✅ GET TRANSACTIONS ACCOUNT NOT FOUND
    @Test
    void testGetTransactions_AccountNotFound() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.getTransactionsByAccountId(1L);
        });
    }
}