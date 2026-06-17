package com.wipro.FinGenieAI.entity;

import java.time.LocalDateTime;

import com.wipro.FinGenieAI.enums.LoanStatus;
import com.wipro.FinGenieAI.enums.LoanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    private Double interestRate;

    private Integer tenure;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Enumerated(EnumType.STRING)
    private LoanType loanType; 

    private LocalDateTime appliedDate;

    private LocalDateTime approvedDate;

    // ✅ Correct relation
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}