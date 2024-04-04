package com.telran.bankapplication.mapper;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.TransactionDto;
import com.telran.bankapplication.entity.*;
import com.telran.bankapplication.entity.enums.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void toEntity_WhenValidDto_Successful() {
        //given
        TransactionDto transactionDto = testDataStorage.getTransactionDto(true, true);

        //when
        Transaction transaction = transactionMapper.toEntity(transactionDto);

        //then
        assertNull(transaction.getId());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
        assertEquals(TransactionType.valueOf(transactionDto.getType()), transaction.getType());
        assertEquals(new BigDecimal(transactionDto.getAmount()), transaction.getAmount());
        assertEquals(transactionDto.getDescription(), transaction.getDescription());
        assertInstanceOf(ZonedDateTime.class, transaction.getCreatedAt());
    }

    @Test
    void toDto_WhenValidEntity_Successful() {
        //given
        Transaction transaction = testDataStorage.getTransaction();
        transaction.setId(1L);
        Account debitAccount = testDataStorage.getAccount();
        debitAccount.setAccountNumber("1234123412341111");
        Account creditAccount = testDataStorage.getAccount();
        creditAccount.setAccountNumber("1234123412341112");

        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);

        //when
        TransactionDto transactionDto = transactionMapper.toDto(transaction);

        //then
        assertEquals(transaction.getId().toString(), transactionDto.getId());
        assertEquals(transaction.getDebitAccount().getAccountNumber(), transactionDto.getDebitAccountNumber());
        assertEquals(transaction.getCreditAccount().getAccountNumber(), transactionDto.getCreditAccountNumber());
        assertEquals(transaction.getStatus().toString(), transactionDto.getStatus());
        assertEquals(transaction.getType().toString(), transactionDto.getType());
        assertEquals(transaction.getAmount().toString(), transactionDto.getAmount());
        assertEquals(transaction.getDescription(), transactionDto.getDescription());
        assertEquals(transaction.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), transactionDto.getCreatedAt());
    }
}