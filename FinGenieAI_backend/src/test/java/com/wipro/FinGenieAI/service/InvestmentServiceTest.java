package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.InvestmentDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Investment;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.InvestmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvestmentServiceImplTest {

    @Mock
    private InvestmentRepository investmentRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private InvestmentServiceImpl investmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ CREATE INVESTMENT SUCCESS
    @Test
    void testCreateInvestment_Success() {

        InvestmentDTO dto = new InvestmentDTO();
        dto.setAccountId(1L);
        dto.setAmount(5000.0);

        Account account = new Account();
        account.setId(1L);
        account.setBalance(20000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(accountRepository.save(any(Account.class)))
                .thenReturn(account);

        Investment savedInvestment = new Investment();
        savedInvestment.setId(100L);

        when(investmentRepository.save(any(Investment.class)))
                .thenReturn(savedInvestment);

        InvestmentDTO result = investmentService.createInvestment(dto);

        assertNotNull(result);

        // ✅ balance reduced check
        assertEquals(15000.0, account.getBalance());

        verify(accountRepository, times(1)).save(account);
        verify(investmentRepository, times(1)).save(any(Investment.class));
    }

    // ✅ ACCOUNT NOT FOUND
    @Test
    void testCreateInvestment_AccountNotFound() {

        InvestmentDTO dto = new InvestmentDTO();
        dto.setAccountId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            investmentService.createInvestment(dto);
        });
    }

    // ✅ INSUFFICIENT BALANCE
    @Test
    void testCreateInvestment_InsufficientBalance() {

        InvestmentDTO dto = new InvestmentDTO();
        dto.setAccountId(1L);
        dto.setAmount(50000.0); // more than balance

        Account account = new Account();
        account.setId(1L);
        account.setBalance(10000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> {
            investmentService.createInvestment(dto);
        });
    }

    // ✅ GET INVESTMENTS SUCCESS
    @Test
    void testGetInvestmentsByAccount_Success() {

        Account account = new Account();
        account.setId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        Investment inv1 = new Investment();
        Investment inv2 = new Investment();

        when(investmentRepository.findByAccountId(1L))
                .thenReturn(List.of(inv1, inv2));

        List<InvestmentDTO> result =
                investmentService.getInvestmentsByAccount(1L);

        assertEquals(2, result.size());
    }

    // ✅ GET INVESTMENTS ACCOUNT NOT FOUND
    @Test
    void testGetInvestmentsByAccount_AccountNotFound() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            investmentService.getInvestmentsByAccount(1L);
        });
    }
}
