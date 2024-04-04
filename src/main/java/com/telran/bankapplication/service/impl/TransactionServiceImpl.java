package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.dto.TransactionDto;
import com.telran.bankapplication.entity.*;
import com.telran.bankapplication.entity.enums.*;
import com.telran.bankapplication.mapper.TransactionMapper;
import com.telran.bankapplication.repository.AccountRepository;
import com.telran.bankapplication.repository.TransactionRepository;
import com.telran.bankapplication.service.*;
import com.telran.bankapplication.service.exception.*;
import com.telran.bankapplication.service.util.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CurrencyService currencyService;

    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction createdTransaction = transactionMapper.toEntity(transactionDto);

        Account debitAccount = accountService.getAccountByNumber(transactionDto.getDebitAccountNumber());
        Account creditAccount = accountService.getAccountByNumber(transactionDto.getCreditAccountNumber());

        makeTransaction(debitAccount, creditAccount, createdTransaction);

        return transactionMapper.toDto(transactionRepository.save(createdTransaction));
    }

    private void makeTransaction(Account debitAccount, Account creditAccount, Transaction transaction) {
        checkAccountIsBlocked(debitAccount, creditAccount);
        checkAccountHasEnoughMoney(debitAccount, transaction);

        BigDecimal conversionRate = getConversionRate(debitAccount.getCurrencyCode(), creditAccount.getCurrencyCode());
        BigDecimal amountDiff = getAmountDifference(transaction.getAmount(), conversionRate);

        updateAccount(debitAccount, debitAccount.getBalance().subtract(transaction.getAmount()));
        updateAccount(creditAccount, creditAccount.getBalance().add(amountDiff));

        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        transaction.setStatus(TransactionStatus.COMPLETED);
    }

    private BigDecimal getConversionRate(CurrencyCode debitCurrency, CurrencyCode creditCurrency) {
        if (debitCurrency == creditCurrency) {
            return BigDecimal.ONE;
        }
        BigDecimal debitRate = currencyService.getCurrencyRate(debitCurrency);
        BigDecimal creditRate = currencyService.getCurrencyRate(creditCurrency);

        return debitRate.divide(creditRate, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal getAmountDifference(BigDecimal amount, BigDecimal conversionRate) {
        return amount.multiply(conversionRate);
    }

    private void updateAccount(Account account, BigDecimal balance) {
        account.setBalance(balance);
        account.setUpdatedAt(ZonedDateTime.now());
        accountRepository.save(account);
    }

    private void checkAccountIsBlocked(Account... accounts) {
        for (Account account : accounts) {
            if (account.getStatus() == AccountStatus.BLOCKED) {
                throw new AccountBlockedException(
                        String.format("Account %s is blocked.", account.getAccountNumber())
                );
            }
        }
    }

    private void checkAccountHasEnoughMoney(Account account, Transaction transaction) {
        if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new NotEnoughMoneyException(
                    String.format("Account %s does not have enough money.", account.getAccountNumber())
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDto> getAllTransactionWhereAccountNumberIs(String number) {
        if (!accountRepository.isAccountNumberExists(number)) {
            throw new EntityNotFoundException(String.format("Account %s not found.", number));
        }
        return transactionMapper.toDtos(transactionRepository.getAllTransactionWhereAccountNumberIs(number));
    }
}
