package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.AccountDTO;
import com.wipro.FinGenieAI.entity.Account;

public class AccountMapper {

    public static AccountDTO toDTO(Account account) {
        if (account == null) return null;

        return AccountDTO.builder()
                .id(account.getId())
                .userId(account.getUserId())   // 🔥 ADD THIS
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .build();
    }

    public static Account toEntity(AccountDTO dto) {
        if (dto == null) return null;

        return Account.builder()
                .id(dto.getId())
                .userId(dto.getUserId()) // 🔥 ADD THIS
                .accountNumber(dto.getAccountNumber())
                .balance(dto.getBalance())
                .accountType(dto.getAccountType())
                .build();
    }
}