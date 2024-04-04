package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.TransactionDto;
import com.telran.bankapplication.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(@RequestBody @Valid TransactionDto transactionDto) {
        return transactionService.createTransaction(transactionDto);
    }

    @GetMapping("/all/account/{number}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionDto> getAllTransactionWhereAccountIs(@PathVariable("number") @Pattern(regexp = "^\\d{16}$") String number) {
        return transactionService.getAllTransactionWhereAccountNumberIs(number);
    }
}
