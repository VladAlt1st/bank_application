package com.telran.bankapplication.mapper;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.*;
import com.telran.bankapplication.entity.enums.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void toEntity_WhenValidDto_Successful() {
        //given
        AccountDto accountDto = testDataStorage.getAccountDto(true, true);

        //when
        Account account = mapper.toEntity(accountDto);

        //then
        assertNull(account.getId());
        assertNull(account.getAccountNumber());
        assertEquals(AccountStatus.ACTIVE, account.getStatus());
        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertEquals(CurrencyCode.valueOf(accountDto.getCurrencyCode()), account.getCurrencyCode());
        assertInstanceOf(ZonedDateTime.class, account.getCreatedAt());
        assertInstanceOf(ZonedDateTime.class, account.getUpdatedAt());
    }

    @Test
    void toDto_WhenValidEntity_Successful() {
        //given
        Account account = testDataStorage.getAccount();
        account.setId(1L);
        User client = testDataStorage.getUser();
        client.setId(1L);
        account.setClient(client);

        //when
        AccountDto accountDto = mapper.toDto(account);

        //then
        assertEquals(account.getId().toString(), accountDto.getId());
        assertEquals(account.getClient().getId().toString(), accountDto.getClientId());
        assertEquals(account.getStatus().toString(), accountDto.getStatus());
        assertEquals(account.getBalance().toString(), accountDto.getBalance());
        assertEquals(account.getCurrencyCode().toString(), accountDto.getCurrencyCode());
        assertEquals(account.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), accountDto.getCreatedAt());
        assertEquals(account.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), accountDto.getUpdatedAt());
    }
}