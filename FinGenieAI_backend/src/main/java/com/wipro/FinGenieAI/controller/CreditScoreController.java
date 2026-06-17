package com.wipro.FinGenieAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.FinGenieAI.dto.CreditScoreDTO;
import com.wipro.FinGenieAI.service.CreditScoreService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/creditscore")
public class CreditScoreController {

    @Autowired
    private CreditScoreService creditScoreService;

    @GetMapping("/{userId}")
    public CreditScoreDTO getScore(@PathVariable Long userId) {
        return creditScoreService.calculateScore(userId);
    }
}