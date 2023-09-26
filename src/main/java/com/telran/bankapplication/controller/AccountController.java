package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.Account;
import com.telran.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }
    @PostMapping("/delete/{accountId}")
    public void deleteAccountById(@PathVariable(name = "accountId") Long accountId) {
        accountService.deleteAccountById(accountId);
    }
}
