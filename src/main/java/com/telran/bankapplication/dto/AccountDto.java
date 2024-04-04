package com.telran.bankapplication.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class AccountDto {

    @Null(message = "Account id must be null.")
    String id;

    @NotNull(message = "Client id can not be null.")
    @Positive(message = "Client id must be positive.")
    String clientId;

    @Null(message = "Account number must be null.")
    String accountNumber;

    @Null(message = "Status must be null.")
    String status;

    @PositiveOrZero(message = "Balance must be positive or zero.")
    String balance;

    @NotBlank(message = "Currency code can not be empty.")
    String currencyCode;

    @Null(message = "Created at must be null.")
    String createdAt;

    @Null(message = "Updated at must be null.")
    String updatedAt;
}
