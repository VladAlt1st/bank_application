package com.telran.bankapplication.service;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.Account;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    void deleteAccountById(Long accountId);

    List<AccountDto> getActiveAccountsWhereProductIdIs(Long productId);

    Account getAccountByNumber(String number);
}
