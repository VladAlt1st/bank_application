package com.telran.bankapplication.service;

import com.telran.bankapplication.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    List<TransactionDto> getAllTransactionWhereAccountNumberIs(String number);
}
