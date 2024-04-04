package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.TransactionDto;
import com.telran.bankapplication.entity.*;
import com.telran.bankapplication.entity.enums.*;
import com.telran.bankapplication.mapper.TransactionMapper;
import com.telran.bankapplication.repository.*;
import com.telran.bankapplication.service.AccountService;
import com.telran.bankapplication.service.exception.*;
import com.telran.bankapplication.service.util.CurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private CurrencyService currencyService;
    @InjectMocks
    private TransactionServiceImpl transactionService;
    private TransactionDto transactionDto;
    private Transaction transaction;
    private Account debitAccount;
    private Account creditAccount;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    private void setUpForCreateTransaction() {
        transactionDto = testDataStorage.getTransactionDto(true, true);
        transaction = testDataStorage.getTransaction();
        debitAccount = testDataStorage.getAccount();
        debitAccount.setId(1L);
        debitAccount.setAccountNumber(transactionDto.getDebitAccountNumber());
        debitAccount.setBalance(new BigDecimal(transactionDto.getAmount()));
        creditAccount = testDataStorage.getAccount();
        creditAccount.setId(2L);
        creditAccount.setAccountNumber(transactionDto.getCreditAccountNumber());

        when(transactionMapper.toEntity(transactionDto)).thenReturn(transaction);
        when(accountService.getAccountByNumber(transactionDto.getDebitAccountNumber())).thenReturn(debitAccount);
        when(accountService.getAccountByNumber(transactionDto.getCreditAccountNumber())).thenReturn(creditAccount);
    }

    @Test
    void createTransaction_WhenValidDataAndSameCurrency_Successful() {
        //given
        setUpForCreateTransaction();
        assertEquals(debitAccount.getCurrencyCode(), creditAccount.getCurrencyCode());
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        //when
        transactionService.createTransaction(transactionDto);

        //then
        verify(transactionMapper).toEntity(transactionDto);
        verify(accountService).getAccountByNumber(transactionDto.getDebitAccountNumber());
        verify(accountService).getAccountByNumber(transactionDto.getCreditAccountNumber());
        assertEquals(BigDecimal.ZERO, debitAccount.getBalance());
        assertEquals(transaction.getAmount(), creditAccount.getBalance());
        verify(accountRepository).save(creditAccount);
        verify(accountRepository).save(debitAccount);
        assertEquals(debitAccount, transaction.getDebitAccount());
        assertEquals(creditAccount, transaction.getCreditAccount());
        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
        verify(transactionRepository).save(transaction);
        verify(transactionMapper).toDto(transaction);
    }

    @Test
    void createTransaction_WhenValidDataAndDifferentCurrency_Successful() {
        //given
        setUpForCreateTransaction();
        BigDecimal usdRate = new BigDecimal("4.012");
        BigDecimal eurRate = new BigDecimal("4.1231");
        BigDecimal conversionRate = usdRate.divide(eurRate,2, RoundingMode.HALF_UP);
        debitAccount.setCurrencyCode(CurrencyCode.USD);
        creditAccount.setCurrencyCode(CurrencyCode.EUR);
        when(currencyService.getCurrencyRate(debitAccount.getCurrencyCode())).thenReturn(usdRate);
        when(currencyService.getCurrencyRate(creditAccount.getCurrencyCode())).thenReturn(eurRate);
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        BigDecimal expectedAmount = debitAccount.getBalance().multiply(conversionRate);

        //when
        transactionService.createTransaction(transactionDto);

        //then
        verify(currencyService).getCurrencyRate(debitAccount.getCurrencyCode());
        verify(currencyService).getCurrencyRate(creditAccount.getCurrencyCode());
        assertEquals(BigDecimal.ZERO, debitAccount.getBalance());
        assertEquals(expectedAmount, creditAccount.getBalance());
    }

    @Test
    void createTransaction_WhenAccountIsBlocked_ThrowAccountBlockedException() {
        //given
        setUpForCreateTransaction();
        debitAccount.setStatus(AccountStatus.BLOCKED);

        //when then
        assertThrows(AccountBlockedException.class, () -> transactionService.createTransaction(transactionDto));
    }

    @Test
    void createTransaction_WhenDebitAccountDoesNotHaveEnoughMoney_ThrowNotEnoughMoneyException() {
        //given
        setUpForCreateTransaction();
        BigDecimal transactionAmount = transaction.getAmount();
        debitAccount.setBalance(transactionAmount.subtract(BigDecimal.ONE));

        //when then
        assertThrows(NotEnoughMoneyException.class, () -> transactionService.createTransaction(transactionDto));
    }

    @Test
    void getAllTransactionWhereAccountNumberIs_WhenAccountExists_Successful() {
        //given
        String number = "1234112233224321";
        List<Transaction> transactions = List.of();
        when(accountRepository.isAccountNumberExists(number)).thenReturn(true);
        when(transactionRepository.getAllTransactionWhereAccountNumberIs(number)).thenReturn(transactions);

        //when
        transactionService.getAllTransactionWhereAccountNumberIs(number);

        //then
        verify(accountRepository).isAccountNumberExists(number);
        verify(transactionRepository).getAllTransactionWhereAccountNumberIs(number);
        verify(transactionMapper).toDtos(transactions);
    }

    @Test
    void getAllTransactionWhereAccountNumberIs_WhenAccountDoesNotExist_ThrowEntityNotFoundException() {
        //given
        String number = "1234112233224321";
        when(accountRepository.isAccountNumberExists(number)).thenReturn(false);

        //when then
        assertThrows(EntityNotFoundException.class, () -> transactionService.getAllTransactionWhereAccountNumberIs(number));
    }
}