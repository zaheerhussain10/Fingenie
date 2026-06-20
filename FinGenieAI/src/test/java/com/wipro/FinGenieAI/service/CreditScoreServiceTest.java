package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.CreditScoreDTO;
import com.wipro.FinGenieAI.entity.*;
import com.wipro.FinGenieAI.enums.LoanStatus;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditScoreServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @InjectMocks
    private CreditScoreServiceImpl creditScoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ TEST SUCCESS FLOW
    @Test
    void testCalculateScore_Success() {

        Long userId = 1L;

        // Accounts
        Account acc = new Account();
        acc.setId(10L);
        acc.setUserId(userId);
        acc.setBalance(60000.0); // high balance

        when(accountRepository.findByUserId(userId))
                .thenReturn(List.of(acc));

        // Transactions
        List<Transaction> txList = Arrays.asList(new Transaction(), new Transaction(),
                new Transaction(), new Transaction(), new Transaction(),
                new Transaction(), new Transaction(), new Transaction(),
                new Transaction(), new Transaction(), new Transaction()); // 11 tx

        when(transactionRepository.findByAccountId(10L))
                .thenReturn(txList);

        // Loan (approved)
        Loan loan = new Loan();
        loan.setAccount(acc);
        loan.setStatus(LoanStatus.APPROVED);

        when(loanRepository.findAll())
                .thenReturn(List.of(loan));

        // Save mock
        CreditScore saved = CreditScore.builder()
                .userId(userId)
                .score(900)
                .rating("EXCELLENT")
                .build();

        when(creditScoreRepository.save(any(CreditScore.class)))
                .thenReturn(saved);

        CreditScoreDTO result = creditScoreService.calculateScore(userId);

        assertNotNull(result);
        assertEquals("EXCELLENT", result.getRating());

        verify(creditScoreRepository, times(1)).save(any(CreditScore.class));
    }

    // ✅ TEST NO ACCOUNT FOUND
    @Test
    void testCalculateScore_NoAccount() {

        when(accountRepository.findByUserId(1L))
                .thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            creditScoreService.calculateScore(1L);
        });
    }

    // ✅ TEST LOW BALANCE + LOW TRANSACTION
    @Test
    void testCalculateScore_LowScore() {

        Long userId = 2L;

        Account acc = new Account();
        acc.setId(20L);
        acc.setUserId(userId);
        acc.setBalance(10000.0); // low balance

        when(accountRepository.findByUserId(userId))
                .thenReturn(List.of(acc));

        // few transactions
        when(transactionRepository.findByAccountId(20L))
                .thenReturn(List.of(new Transaction(), new Transaction()));

        // no approved loan
        when(loanRepository.findAll())
                .thenReturn(List.of());

        CreditScore saved = CreditScore.builder()
                .userId(userId)
                .score(500)
                .rating("POOR")
                .build();

        when(creditScoreRepository.save(any(CreditScore.class)))
                .thenReturn(saved);

        CreditScoreDTO result = creditScoreService.calculateScore(userId);

        assertNotNull(result);
        assertEquals("POOR", result.getRating());
    }

    // ✅ TEST SCORE LIMIT ( > 900 should cap to 900 )
    @Test
    void testScoreLimit() {

        Long userId = 3L;

        Account acc = new Account();
        acc.setId(30L);
        acc.setUserId(userId);
        acc.setBalance(100000.0);

        when(accountRepository.findByUserId(userId))
                .thenReturn(List.of(acc));

        when(transactionRepository.findByAccountId(30L))
                .thenReturn(Collections.nCopies(20, new Transaction()));

        Loan loan = new Loan();
        loan.setAccount(acc);
        loan.setStatus(LoanStatus.APPROVED);

        when(loanRepository.findAll())
                .thenReturn(List.of(loan));

        when(creditScoreRepository.save(any(CreditScore.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CreditScoreDTO result = creditScoreService.calculateScore(userId);

        assertTrue(result.getScore() <= 900);
    }
}
