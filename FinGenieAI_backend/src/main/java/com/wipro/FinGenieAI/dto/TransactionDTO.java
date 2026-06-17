package com.wipro.FinGenieAI.dto;

import java.time.LocalDateTime;

import com.wipro.FinGenieAI.enums.TransactionStatus;
import com.wipro.FinGenieAI.enums.TransactionType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long id;

    // ✅ For deposit & withdraw
    private Long accountId;

    // ✅ For transfer
    private Long fromAccountId;
    private Long toAccountId;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;

    private TransactionStatus status;
    private TransactionType type;

    private LocalDateTime timestamp;
}
