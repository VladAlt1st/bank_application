package com.telran.bankapplication.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class TransactionDto {

    @Null(message = "Transaction id must be null.")
    String id;

    @Pattern(regexp = "^\\d{16}$",
            message = "Invalid debit number format.")
    String debitAccountNumber;

    @Pattern(regexp = "^\\d{16}$",
            message = "Invalid credit number format.")
    String creditAccountNumber;

    @Null(message = "Status must be null.")
    String status;

    @NotBlank(message = "Type can not be empty.")
    String type;

    @Positive(message = "Transaction amount must be positive.")
    String amount;

    @Size(max = 255)
    String description;

    @Null(message = "Created at must be null.")
    String createdAt;
}
