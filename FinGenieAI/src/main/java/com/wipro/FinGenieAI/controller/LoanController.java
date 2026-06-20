package com.wipro.FinGenieAI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.FinGenieAI.dto.LoanDTO;
import com.wipro.FinGenieAI.service.LoanService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // ✅ Apply Loan
    @PostMapping
    public LoanDTO applyLoan(@Valid @RequestBody LoanDTO dto) {
        return loanService.applyLoan(dto);
    }
    @PutMapping("/{id}/approve")
    public String approveLoan(@PathVariable Long id) {
        loanService.updateStatus(id, "APPROVED");
        return "Approved";
    }

    @PutMapping("/{id}/reject")
    public String rejectLoan(@PathVariable Long id) {
        loanService.updateStatus(id, "REJECTED");
        return "Rejected";
    }

    @GetMapping("/{id}")
    public LoanDTO getLoan(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }
    @GetMapping
    public List<LoanDTO> getAllLoans() {
        return loanService.getAllLoans();
    }
   
}
