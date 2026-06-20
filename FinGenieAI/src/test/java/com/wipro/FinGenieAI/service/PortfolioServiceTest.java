package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.PortfolioDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Investment;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.InvestmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortfolioServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private InvestmentRepository investmentRepository;

    @InjectMocks
    private PortfolioServiceImpl portfolioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ SUCCESS CASE
    @Test
    void testGetPortfolio_Success() {

        Long accountId = 1L;

        Account account = new Account();
        account.setId(accountId);
        account.setBalance(20000.0);

        Investment inv1 = new Investment();
        inv1.setAmount(5000.0);
        inv1.setReturns(500.0);

        Investment inv2 = new Investment();
        inv2.setAmount(10000.0);
        inv2.setReturns(1500.0);

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(investmentRepository.findByAccountId(accountId))
                .thenReturn(List.of(inv1, inv2));

        PortfolioDTO result = portfolioService.getPortfolio(accountId);

        assertNotNull(result);

        // ✅ verify calculations
        assertEquals(15000.0, result.getTotalInvestment());
        assertEquals(2000.0, result.getTotalReturns());
        assertEquals(2, result.getNumberOfInvestments());
        assertEquals(20000.0, result.getCurrentBalance());
    }

    // ✅ ACCOUNT NOT FOUND
    @Test
    void testGetPortfolio_AccountNotFound() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            portfolioService.getPortfolio(1L);
        });
    }

    // ✅ NO INVESTMENTS CASE
    @Test
    void testGetPortfolio_NoInvestments() {

        Long accountId = 2L;

        Account account = new Account();
        account.setId(accountId);
        account.setBalance(10000.0);

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(investmentRepository.findByAccountId(accountId))
                .thenReturn(List.of());

        PortfolioDTO result = portfolioService.getPortfolio(accountId);

        assertNotNull(result);

        assertEquals(0.0, result.getTotalInvestment());
        assertEquals(0.0, result.getTotalReturns());
        assertEquals(0, result.getNumberOfInvestments());
        assertEquals(10000.0, result.getCurrentBalance());
    }
}
