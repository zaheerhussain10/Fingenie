package com.wipro.FinGenieAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.FinGenieAI.dto.LoanDTO;
import com.wipro.FinGenieAI.service.LoanServiceTest;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanServiceTest loanService;

    // ✅ Apply Loan
    @PostMapping
    public LoanDTO applyLoan(@Valid @RequestBody LoanDTO dto) {
        return loanService.applyLoan(dto);
    }

    // ✅ Get Loan by ID
    @GetMapping("/{id}")
    public LoanDTO getLoan(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }
}
