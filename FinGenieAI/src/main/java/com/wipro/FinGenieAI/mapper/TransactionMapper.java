package com.wipro.FinGenieAI.mapper;

import com.wipro.FinGenieAI.dto.TransactionDTO;
import com.wipro.FinGenieAI.entity.Transaction;

public class TransactionMapper {

    public static TransactionDTO toDTO(Transaction tx) {
        if (tx == null) return null;

        return TransactionDTO.builder()
                .id(tx.getId())
                .amount(tx.getAmount())

                // ✅ FIX: map accountId from entity
                .accountId(
                        tx.getAccount() != null 
                        ? tx.getAccount().getId() 
                        : null
                )

                // ✅ transfer fields
                .toAccountId(tx.getToAccountId())

                // ✅ optional (for clarity in transfer)
                .fromAccountId(
                        tx.getAccount() != null
                        ? tx.getAccount().getId()
                        : null
                )

                .status(tx.getStatus())
                .type(tx.getType())
                .timestamp(tx.getTimestamp())
                .build();
    }

    public static Transaction toEntity(TransactionDTO dto) {
        if (dto == null) return null;

        return Transaction.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .status(dto.getStatus())
                .type(dto.getType())
                .timestamp(dto.getTimestamp())

                // ✅ transfer support
                .toAccountId(dto.getToAccountId())

                .build();
    }
}