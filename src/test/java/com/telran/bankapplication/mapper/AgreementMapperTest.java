package com.telran.bankapplication.mapper;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.AgreementDto;
import com.telran.bankapplication.entity.Agreement;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class AgreementMapperTest {

    private final AgreementMapper agreementMapper = Mappers.getMapper(AgreementMapper.class);
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void toDto_WhenValidEntity_Successful() {
        //given
        Agreement agreement = testDataStorage.getAgreement();

        //when
        AgreementDto agreementDto = agreementMapper.toDto(agreement);

        //then
        assertEquals(agreement.getId().toString(), agreementDto.getId());
        assertEquals(agreement.getAccount().getId().toString(), agreementDto.getAccountId());
        assertEquals(agreement.getManager().getId().toString(), agreementDto.getManagerId());
        assertEquals(agreement.getProduct().getId().toString(), agreementDto.getProductId());
        assertEquals(agreement.getStatus().toString(), agreementDto.getStatus());
        assertEquals(agreement.getSum().toString(), agreementDto.getSum());
        assertEquals(agreement.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), agreementDto.getCreatedAt());
        assertEquals(agreement.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), agreementDto.getUpdatedAt());
    }
}