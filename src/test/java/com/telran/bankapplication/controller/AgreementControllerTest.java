package com.telran.bankapplication.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.*;
import com.telran.bankapplication.entity.enums.ProductType;
import com.telran.bankapplication.service.AgreementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AgreementController.class)
class AgreementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AgreementService agreementService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void getAllAgreementsWhereProductTypeIs_WhenValidProductType_Successful() throws Exception {
        //given
        String productType = "CREDIT";
        List<AgreementDto> expectedList = List.of(testDataStorage.getAgreementDto());
        when(agreementService.getAllAgreementsWhereProductTypeIs(ProductType.valueOf(productType))).thenReturn(expectedList);

        //when
        String dtoListJson = mockMvc.perform(get("/agreement/all/{productType}", productType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<AgreementDto> actualList = objectMapper.readValue(dtoListJson, new TypeReference<>() {});

        //then
        assertEquals(expectedList, actualList);
        verify(agreementService).getAllAgreementsWhereProductTypeIs(ProductType.valueOf(productType));
    }

    @Test
    void getAllAgreementsWhereProductTypeIs_WhenInvalidProductType_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        String invalidProductType = "CREDT";
        String expectedMessage = "No enum constant com.telran.bankapplication.entity.enums.ProductType.CREDT";

        //when
        String errorDataJson = mockMvc.perform(get("/agreement/all/{productType}", invalidProductType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(agreementService);
    }
}