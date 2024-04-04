package com.telran.bankapplication.dto;

import lombok.Value;

@Value
public class ProductDto {
    String id;
    String name;
    String type;
    String status;
    String currencyCode;
    String interestRate;
    String productLimit;
    String createdAt;
    String updatedAt;
}
