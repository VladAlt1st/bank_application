package com.telran.bankapplication.mapper;

import com.telran.bankapplication.dto.AgreementDto;
import com.telran.bankapplication.entity.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR,
        imports = {ZonedDateTime.class})
public interface AgreementMapper {

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "createdAt", source = "createdAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    AgreementDto toDto(Agreement agreement);

    List<AgreementDto> toDtos(List<Agreement> agreements);
}
