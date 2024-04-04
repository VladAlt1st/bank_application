package com.telran.bankapplication.mapper;

import com.telran.bankapplication.dto.AccountDto;
import com.telran.bankapplication.entity.Account;
import org.mapstruct.*;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR,
        imports = {ZonedDateTime.class})
public interface AccountMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "balance", constant = "0")
    @Mapping(target = "createdAt", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(ZonedDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account toEntity(AccountDto accountDto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "createdAt", source = "createdAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    AccountDto toDto(Account account);

    List<AccountDto> toDtos(List<Account> accounts);
}
