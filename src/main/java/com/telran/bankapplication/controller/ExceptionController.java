package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.ErrorData;
import com.telran.bankapplication.service.exception.EntityNotFoundException;
import com.telran.bankapplication.service.exception.UserAlreadyExistsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorData handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return new ErrorData(LocalDateTime.now(), HttpStatus.BAD_REQUEST, getMessage(exception));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorData handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return new ErrorData(LocalDateTime.now(), HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorData handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ErrorData(LocalDateTime.now(), HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorData handleConstraintViolationException(ConstraintViolationException exception) {
        return new ErrorData(LocalDateTime.now(), HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorData handleEntityNotFoundException(EntityNotFoundException exception) {
        return new ErrorData(LocalDateTime.now(), HttpStatus.NO_CONTENT, exception.getMessage());
    }

    private String getMessage(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
