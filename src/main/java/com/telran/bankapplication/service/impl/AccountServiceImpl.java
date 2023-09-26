package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.Account;
import com.telran.bankapplication.entity.enums.AccountStatus;
import com.telran.bankapplication.entity.enums.CurrencyCode;
import com.telran.bankapplication.repository.AccountRepository;
import com.telran.bankapplication.repository.UserRepository;
import com.telran.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Account createAccount(AccountDto accountDto) {
        Account account = new Account();

        account.setClient(userRepository.findById(accountDto.getClientId()).orElseThrow(
                NoSuchElementException::new
        ));
        account.setName(accountDto.getName());
        account.setStatus(AccountStatus.valueOf(accountDto.getStatus()));
        account.setBalance(accountDto.getBalance());
        account.setCurrencyCode(CurrencyCode.valueOf(accountDto.getCurrencyCode()));

        return accountRepository.save(account);
    }
}
