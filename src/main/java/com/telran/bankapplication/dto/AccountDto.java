package com.telran.bankapplication.dto;

import lombok.Value;

@Value
public class AccountDto {
    Long clientId;
    String name;
    String status;
    String balance;
    String currencyCode;
}
