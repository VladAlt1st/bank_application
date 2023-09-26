package com.telran.bankapplication.service;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.Account;

public interface AccountService {
    Account createAccount(AccountDto accountDto);

    void deleteAccountById(Long accountId);
}
