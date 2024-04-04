package com.telran.bankapplication.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class ErrorData {

    private final LocalDateTime timestamp;

    private final HttpStatus status;

    private final String message;
}
