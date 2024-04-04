package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.*;
import com.telran.bankapplication.mapper.AccountMapper;
import com.telran.bankapplication.repository.*;
import com.telran.bankapplication.service.AccountService;
import com.telran.bankapplication.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        Account createdAccount = accountMapper.toEntity(accountDto);

        User client = userRepository.findById(Long.valueOf(accountDto.getClientId())).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client with id %s not found.", accountDto.getClientId()))
        );
        createdAccount.setClient(client);
        createdAccount.setAccountNumber(createAccountNumber());

        return accountMapper.toDto(accountRepository.save(createdAccount));
    }

    @Override
    @Transactional
    public void deleteAccountById(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new EntityNotFoundException(String.format("Account with id %s not found.", accountId));
        }
        accountRepository.deleteById(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDto> getActiveAccountsWhereProductIdIs(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(String.format("Product with id %s not found.", productId));
        }
        return accountMapper.toDtos(accountRepository.getActiveAccountsWhereProductIdIs(productId));
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.getAccountByAccountNumber(number).orElseThrow(
                () -> new EntityNotFoundException(String.format("Account %s not found.", number))
        );
    }

    private String createAccountNumber() {
        String number = RandomStringUtils.randomNumeric(16);

        while (accountRepository.isAccountNumberExists(number)) {
            number = RandomStringUtils.randomNumeric(16);
        }
        return number;
    }
}
