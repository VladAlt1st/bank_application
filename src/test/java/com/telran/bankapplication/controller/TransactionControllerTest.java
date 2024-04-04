package com.telran.bankapplication.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.*;
import com.telran.bankapplication.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void createTransaction_WhenValidInput_InvokeCreateTransactionAndReturnsIsCreated() throws Exception {
        //given
        TransactionDto transactionDto = testDataStorage.getTransactionDto(true, true);
        String transactionDtoJson = objectMapper.writeValueAsString(transactionDto);
        TransactionDto expectedDto = testDataStorage.getTransactionDto(true, false);
        when(transactionService.createTransaction(transactionDto)).thenReturn(expectedDto);

        //when
        String actualDtoJson = mockMvc.perform(post("/transaction/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionDtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        TransactionDto actualDto = objectMapper.readValue(actualDtoJson, TransactionDto.class);

        //then
        assertEquals(expectedDto, actualDto);
        verify(transactionService).createTransaction(transactionDto);
    }

    @Test
    void createTransaction_WhenInvalidInput_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        TransactionDto invalidTransactionDto = testDataStorage.getTransactionDto(false, true);
        String invalidTransactionDtoJson = objectMapper.writeValueAsString(invalidTransactionDto);
        String expectedMessage = "Invalid debit number format.";

        //when
        String errorDataJson = mockMvc.perform(post("/transaction/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTransactionDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(transactionService);
    }

    @Test
    void getAllTransactionWhereAccountIs_WhenValidNumber_InvokeGetAllTransactionWhereAccountNumberIsAndReturnsIsOk() throws Exception {
        //given
        String number = "1234123412341111";
        List<TransactionDto> expectedList = List.of(testDataStorage.getTransactionDto(true, false));
        when(transactionService.getAllTransactionWhereAccountNumberIs(number)).thenReturn(expectedList);

        //when
        String dtoListJson = mockMvc.perform(get("/transaction/all/account/{number}", number)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<TransactionDto> actualList = objectMapper.readValue(dtoListJson, new TypeReference<>() {});

        //then
        assertEquals(expectedList, actualList);
        verify(transactionService).getAllTransactionWhereAccountNumberIs(number);
    }

    @Test
    void getAllTransactionWhereAccountIs_WhenInvalidNumber_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        String invalidNumber = "12341234123411Aa";
        String expectedMessage = "getAllTransactionWhereAccountIs.number: must match \"^\\d{16}$\"";

        //when
        String errorDataJson = mockMvc.perform(get("/transaction/all/account/{number}", invalidNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(transactionService);
    }
}