package com.wipro.FinGenieAI.entity;

import java.time.LocalDateTime;

import com.wipro.FinGenieAI.enums.InvestmentType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InvestmentType type; 

    @Column(nullable = false)
    private Double amount; 

    private Double returns;

    private LocalDateTime createdAt; 

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account; 

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}