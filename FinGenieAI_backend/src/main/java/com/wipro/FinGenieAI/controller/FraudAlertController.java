package com.wipro.FinGenieAI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.FinGenieAI.dto.FraudAlertDTO;
import com.wipro.FinGenieAI.service.FraudAlertService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/fraud")
public class FraudAlertController {

    @Autowired
    private FraudAlertService fraudAlertService;

    @GetMapping("/{transactionId}")
    public FraudAlertDTO checkFraud(@PathVariable Long transactionId) {
        return fraudAlertService.checkFraud(transactionId);
    }
}