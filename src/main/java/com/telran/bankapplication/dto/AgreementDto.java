package com.telran.bankapplication.dto;

import lombok.Value;

@Value
public class AgreementDto {
    String id;
    String accountId;
    String managerId;
    String productId;
    String status;
    String sum;
    String createdAt;
    String updatedAt;
}
