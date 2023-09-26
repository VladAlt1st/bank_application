package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.repository.TransactionRepository;
import com.telran.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
