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

import com.wipro.FinGenieAI.dto.AccountDTO;
import com.wipro.FinGenieAI.service.AccountService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // ✅ CREATE ACCOUNT
    @PostMapping
    public AccountDTO createAccount(@Valid @RequestBody AccountDTO dto) {
        return accountService.createAccount(dto);
    }

    // ✅ GET ACCOUNT BY ID
    @GetMapping("/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    // ✅ GET ALL ACCOUNTS OF A USER
    @GetMapping("/my")
    public List<AccountDTO> getMyAccounts() {
        return accountService.getMyAccounts();
    }
}