package com.wipro.FinGenieAI.service;

import java.util.List;

import com.wipro.FinGenieAI.dto.AccountDTO;

public interface AccountService {

    AccountDTO createAccount(AccountDTO dto);

    AccountDTO getAccountById(Long id);

    List<AccountDTO> getAccountsByUserId(Long userId);

    List<AccountDTO> getMyAccounts(); 
}