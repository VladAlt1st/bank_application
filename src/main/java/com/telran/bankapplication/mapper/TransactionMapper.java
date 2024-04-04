package com.telran.bankapplication.mapper;

import com.telran.bankapplication.dto.TransactionDto;
import com.telran.bankapplication.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR,
        imports = {ZonedDateTime.class})
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(ZonedDateTime.now())")
    Transaction toEntity(TransactionDto transactionDto);

    @Mapping(target = "debitAccountNumber", source = "debitAccount.accountNumber")
    @Mapping(target = "creditAccountNumber", source = "creditAccount.accountNumber")
    @Mapping(target = "createdAt", source = "createdAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    TransactionDto toDto(Transaction transaction);

    List<TransactionDto> toDtos(List<Transaction> transactions);
}
