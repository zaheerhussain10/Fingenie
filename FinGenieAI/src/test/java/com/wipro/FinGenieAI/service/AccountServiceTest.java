package com.wipro.FinGenieAI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wipro.FinGenieAI.dto.AccountDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.User;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.mapper.AccountMapper;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.UserRepository;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // ✅ Mock SecurityContext (JWT email)
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("test@gmail.com", null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // ✅ TEST CREATE ACCOUNT SUCCESS
    @Test
    void testCreateAccount_Success() {

        AccountDTO dto = new AccountDTO();
        dto.setBalance(2000.0);

        User user = new User();
        user.setId(1L);

        Account account = AccountMapper.toEntity(dto);
        account.setUserId(1L);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(accountRepository.save(any(Account.class)))
                .thenReturn(account);

        AccountDTO result = accountService.createAccount(dto);

        assertNotNull(result);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    // ✅ TEST MIN BALANCE VALIDATION
    @Test
    void testCreateAccount_MinBalanceFail() {

        AccountDTO dto = new AccountDTO();
        dto.setBalance(500.0); // less than 1000

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountService.createAccount(dto);
        });

        assertEquals("Minimum balance must be 1000", ex.getMessage());
    }

    // ✅ TEST USER NOT FOUND
    @Test
    void testCreateAccount_UserNotFound() {

        AccountDTO dto = new AccountDTO();
        dto.setBalance(2000.0);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accountService.createAccount(dto);
        });

        assertEquals("User not found", ex.getMessage());
    }

    // ✅ TEST GET ACCOUNT BY ID SUCCESS
    @Test
    void testGetAccountById_Success() {

        Account account = new Account();
        account.setId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        AccountDTO result = accountService.getAccountById(1L);

        assertNotNull(result);
    }

    // ✅ TEST GET ACCOUNT BY ID NOT FOUND
    @Test
    void testGetAccountById_NotFound() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccountById(1L);
        });
    }

    // ✅ TEST GET ACCOUNTS BY USER ID
    @Test
    void testGetAccountsByUserId() {

        Account acc1 = new Account();
        Account acc2 = new Account();

        when(accountRepository.findByUserId(1L))
                .thenReturn(Arrays.asList(acc1, acc2));

        List<AccountDTO> result = accountService.getAccountsByUserId(1L);

        assertEquals(2, result.size());
    }

    // ✅ TEST GET MY ACCOUNTS (JWT FLOW)
    @Test
    void testGetMyAccounts() {

        User user = new User();
        user.setId(1L);

        Account acc = new Account();

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(accountRepository.findByUserId(1L))
                .thenReturn(List.of(acc));

        List<AccountDTO> result = accountService.getMyAccounts();

        assertEquals(1, result.size());
    }
}
