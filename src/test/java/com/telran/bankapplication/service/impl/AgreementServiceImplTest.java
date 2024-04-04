package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.AgreementDto;
import com.telran.bankapplication.entity.Agreement;
import com.telran.bankapplication.entity.enums.ProductType;
import com.telran.bankapplication.mapper.AgreementMapper;
import com.telran.bankapplication.repository.AgreementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementServiceImplTest {

    @Mock
    private AgreementRepository agreementRepository;
    @Mock
    private AgreementMapper agreementMapper;
    @InjectMocks
    private AgreementServiceImpl agreementService;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void getAllAgreementsWhereProductTypeIs_Successful() {
        //given
        ProductType productType = ProductType.CREDIT;
        List<Agreement> agreements = List.of(testDataStorage.getAgreement());
        List<AgreementDto> expectedList = List.of(testDataStorage.getAgreementDto());
        when(agreementRepository.getAllAgreementsWhereProductTypeIs(productType)).thenReturn(agreements);
        when(agreementMapper.toDtos(agreements)).thenReturn(expectedList);

        //when
        List<AgreementDto> actualList = agreementService.getAllAgreementsWhereProductTypeIs(productType);

        //then
        assertEquals(expectedList, actualList);
        verify(agreementRepository).getAllAgreementsWhereProductTypeIs(productType);
        verify(agreementMapper).toDtos(agreements);
    }
}