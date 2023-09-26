package com.telran.bankapplication.controller;

import com.telran.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/delete/{transactionId}")
    public void deleteTransactionById(@PathVariable(name = "transactionId") Long transactionId) {
        transactionService.deleteTransactionById(transactionId);
    }
}
