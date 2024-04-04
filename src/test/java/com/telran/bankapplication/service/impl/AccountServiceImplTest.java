package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.*;
import com.telran.bankapplication.mapper.AccountMapper;
import com.telran.bankapplication.repository.*;
import com.telran.bankapplication.service.exception.EntityNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AccountServiceImpl accountService;
    private AccountDto accountDto;
    private Account account;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    private void setUpForCreateAccount() {
        accountDto = testDataStorage.getAccountDto(true, true);
        account = testDataStorage.getAccount();
        when(accountMapper.toEntity(accountDto)).thenReturn(account);
    }

    @Test
    void createAccount_WhenValidData_Successful() {
        //given
        setUpForCreateAccount();
        User client = testDataStorage.getUser();
        when(userRepository.findById(Long.valueOf(accountDto.getClientId()))).thenReturn(Optional.of(client));

        try (MockedStatic<RandomStringUtils> mockedRandom = mockStatic(RandomStringUtils.class)) {
            mockedRandom.when(() -> RandomStringUtils.randomNumeric(16))
                    .thenReturn("1234567890123456")
                    .thenReturn("6543210987654321");
            when(accountRepository.isAccountNumberExists("1234567890123456")).thenReturn(true);
            when(accountRepository.isAccountNumberExists("6543210987654321")).thenReturn(false);
            when(accountRepository.save(account)).thenReturn(account);

            //when
            accountService.createAccount(accountDto);
        }
        //then
        verify(accountMapper).toEntity(accountDto);
        verify(userRepository).findById(Long.valueOf(accountDto.getClientId()));
        assertEquals(client, account.getClient());
        assertEquals("6543210987654321", account.getAccountNumber());
        assertTrue(account.getAccountNumber().matches("^\\d{16}$"));
        verify(accountRepository).save(account);
        verify(accountMapper).toDto(account);
    }

    @Test
    void createAccount_WhenClientDoesNotExist_ThrowEntityNotFountException() {
        //given
        setUpForCreateAccount();
        when(userRepository.findById(Long.valueOf(accountDto.getClientId()))).thenReturn(Optional.empty());

        //when then
        assertThrows(EntityNotFoundException.class, () -> accountService.createAccount(accountDto));
    }

    @Test
    void deleteAccountById_WhenAccountExists_InvokeDeleteById() {
        //given
        Long accountId = 1L;
        when(accountRepository.existsById(accountId)).thenReturn(true);

        //when
        accountService.deleteAccountById(accountId);

        //then
        verify(accountRepository).existsById(accountId);
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void deleteAccountById_WhenAccountDoesNotExist_ThrowEntityNotFountException() {
        //given
        Long accountId = 1L;
        when(accountRepository.existsById(accountId)).thenReturn(false);

        //when then
        assertThrows(EntityNotFoundException.class, () -> accountService.deleteAccountById(accountId));
    }

    @Test
    void getActiveAccountsWhereProductIdIs_WhenProductExists_Successful() {
        //given
        Long productId = 1L;
        List<Account> accounts = List.of();
        when(productRepository.existsById(productId)).thenReturn(true);
        when(accountRepository.getActiveAccountsWhereProductIdIs(productId)).thenReturn(accounts);

        //when
        accountService.getActiveAccountsWhereProductIdIs(productId);

        //then
        verify(productRepository).existsById(productId);
        verify(accountRepository).getActiveAccountsWhereProductIdIs(productId);
        verify(accountMapper).toDtos(accounts);
    }

    @Test
    void getActiveAccountsWhereProductIdIs_WhenProductDoesNotExist_ThrowEntityNotFountException() {
        //given
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);

        //when then
        assertThrows(EntityNotFoundException.class, () -> accountService.getActiveAccountsWhereProductIdIs(productId));
    }

    @Test
    void getAccountByNumber_WhenAccountExist_Successful() {
        //given
        String number = "1234112233224321";
        Account expectedAccount = testDataStorage.getAccount();
        expectedAccount.setId(1L);
        expectedAccount.setAccountNumber(number);
        when(accountRepository.getAccountByAccountNumber(number)).thenReturn(Optional.of(expectedAccount));

        //when
        Account actualAccount = accountService.getAccountByNumber(number);

        //then
        assertEquals(expectedAccount, actualAccount);
        verify(accountRepository).getAccountByAccountNumber(number);
    }

    @Test
    void getAccountByNumber_WhenAccountDoesNotExist_ThrowEntityNotFountException() {
        //given
        String number = "1234112233224321";
        when(accountRepository.getAccountByAccountNumber(number)).thenReturn(Optional.empty());

        //when then
        assertThrows(EntityNotFoundException.class, () -> accountService.getAccountByNumber(number));
    }
}