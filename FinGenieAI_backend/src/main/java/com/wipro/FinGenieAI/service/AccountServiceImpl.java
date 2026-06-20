package com.wipro.FinGenieAI.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wipro.FinGenieAI.dto.AccountDTO;
import com.wipro.FinGenieAI.entity.Account;
import com.wipro.FinGenieAI.entity.User;
import com.wipro.FinGenieAI.exception.ResourceNotFoundException;
import com.wipro.FinGenieAI.mapper.AccountMapper;
import com.wipro.FinGenieAI.repository.AccountRepository;
import com.wipro.FinGenieAI.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository; 

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    // ✅ CREATE ACCOUNT
    @Override
    public AccountDTO createAccount(AccountDTO dto) {

        logger.info("Creating account");

        if (dto.getBalance() < 1000) {
            throw new RuntimeException("Minimum balance must be 1000");
        }

        // ✅ GET EMAIL FROM JWT
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accNumber = "ACC-" + UUID.randomUUID().toString().substring(0, 8);

        Account account = AccountMapper.toEntity(dto);

        account.setAccountNumber(accNumber);

        // ✅ SET USER ID
        account.setUserId(user.getId());

        Account saved = accountRepository.save(account);

        logger.info("Account created successfully");

        return AccountMapper.toDTO(saved);
    }


    // ✅ GET ACCOUNT BY ID
    @Override
    public AccountDTO getAccountById(Long id) {

        logger.info("Fetching account with id: {}", id);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Account not found with id: {}", id);
                    return new ResourceNotFoundException("Account not found");
                });

        return AccountMapper.toDTO(account);
    }

    // ✅ GET ACCOUNTS BY USER
    @Override
    public List<AccountDTO> getAccountsByUserId(Long userId) {

        logger.info("Fetching accounts for userId: {}", userId);

        return accountRepository.findByUserId(userId)
                .stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<AccountDTO> getMyAccounts() {

        // ✅ GET EMAIL FROM JWT
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUserId(user.getId())
                .stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<AccountDTO> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(AccountMapper::toDTO)
                .toList();
    }
}