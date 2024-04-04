package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @DeleteMapping("/delete/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccountById(@PathVariable(name = "accountId") @Positive Long accountId) {
        accountService.deleteAccountById(accountId);
    }

    @GetMapping("/active/with-product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> findActiveAccountsWhereProductIdIs(@PathVariable("productId") @Positive Long productId) {
        return accountService.getActiveAccountsWhereProductIdIs(productId);
    }
}
