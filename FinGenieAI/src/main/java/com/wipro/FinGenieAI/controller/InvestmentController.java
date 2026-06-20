package com.wipro.FinGenieAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.FinGenieAI.dto.InvestmentDTO;
import com.wipro.FinGenieAI.service.InvestmentService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/investments")
public class InvestmentController {

    @Autowired
    private InvestmentService investmentService;

    // ✅ Create investment
    @PostMapping
    public InvestmentDTO create(@Valid @RequestBody InvestmentDTO dto) {
        return investmentService.createInvestment(dto);
    }

    // ✅ Get investments by account
    @GetMapping("/account/{accountId}")
    public List<InvestmentDTO> getByAccount(@PathVariable Long accountId) {
        return investmentService.getInvestmentsByAccount(accountId);
    }
}