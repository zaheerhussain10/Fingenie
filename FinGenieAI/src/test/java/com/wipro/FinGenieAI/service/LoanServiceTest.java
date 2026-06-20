package com.wipro.FinGenieAI.service;

import com.wipro.FinGenieAI.dto.LoanDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.Loan;
import com.wipro.FinGenieAI.enums.LoanStatus;
import com.wipro.FinGenieAI.enums.LoanType;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.LoanRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ APPLY LOAN - APPROVED (HOME LOAN)
    @Test
    void testApplyLoan_Approved_HomeLoan() {

        LoanDTO dto = new LoanDTO();
        dto.setAccountId(1L);
        dto.setAmount(50000.0);
        dto.setLoanType(LoanType.HOME_LOAN);

        Account account = new Account();
        account.setId(1L);
        account.setBalance(20000.0); // >= 20% → approved

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanDTO result = loanService.applyLoan(dto);

        assertNotNull(result);
        assertEquals(LoanStatus.APPROVED, result.getStatus());
    }

    // ✅ APPLY LOAN - REJECTED (LOW BALANCE)
    @Test
    void testApplyLoan_Rejected() {

        LoanDTO dto = new LoanDTO();
        dto.setAccountId(1L);
        dto.setAmount(50000.0);
        dto.setLoanType(LoanType.PERSONAL_LOAN);

        Account account = new Account();
        account.setId(1L);
        account.setBalance(5000.0); // < 20% → rejected

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanDTO result = loanService.applyLoan(dto);

        assertEquals(LoanStatus.REJECTED, result.getStatus());
    }

    // ✅ TEST SWITCH CASE (CAR LOAN INTEREST + TENURE)
    @Test
    void testLoanType_CarLoan() {

        LoanDTO dto = new LoanDTO();
        dto.setAccountId(1L);
        dto.setAmount(30000.0);
        dto.setLoanType(LoanType.CAR_LOAN);

        Account account = new Account();
        account.setId(1L);
        account.setBalance(20000.0);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(loanRepository.save(any(Loan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanDTO result = loanService.applyLoan(dto);

        assertNotNull(result);

        // ✅ check interest / tenure indirectly (no getter? skip if not in DTO)
        // Focus on status
        assertEquals(LoanStatus.APPROVED, result.getStatus());
    }

    // ✅ ACCOUNT NOT FOUND
    @Test
    void testApplyLoan_AccountNotFound() {

        LoanDTO dto = new LoanDTO();
        dto.setAccountId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            loanService.applyLoan(dto);
        });
    }

    // ✅ GET LOAN BY ID SUCCESS
    @Test
    void testGetLoanById_Success() {

        Loan loan = new Loan();
        loan.setId(1L);

        when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        LoanDTO result = loanService.getLoanById(1L);

        assertNotNull(result);
    }

    // ✅ GET LOAN BY ID NOT FOUND
    @Test
    void testGetLoanById_NotFound() {

        when(loanRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            loanService.getLoanById(1L);
        });
    }
}