package com.telran.bankapplication.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccountDto {
    Long clientId;
    String name;
    String status;
    BigDecimal balance;
    String currencyCode;
}
