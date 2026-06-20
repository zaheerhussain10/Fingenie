package com.wipro.FinGenieAI.entity;

import java.time.LocalDateTime;

import com.wipro.FinGenieAI.enums.TransactionStatus;
import com.wipro.FinGenieAI.enums.TransactionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // CREDIT / DEBIT

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String category;

    // ✅ IMPORTANT for transfer
    private Long toAccountId;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    private FraudAlert fraudAlert;

    // ✅ Auto timestamp
    @PrePersist
    public void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
