package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.Account;
import com.telran.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }
}
