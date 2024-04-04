package com.telran.bankapplication.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.*;
import com.telran.bankapplication.service.AccountService;
import com.telran.bankapplication.service.exception.EntityNotFoundException;
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

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @Autowired
    private ObjectMapper objectMapper;

    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void createAccount_WhenValidInput_InvokeCreateAccountAndReturnsIsCreated() throws Exception {
        //given
        AccountDto accountDto = testDataStorage.getAccountDto(true, true);
        String accountDtoJson = objectMapper.writeValueAsString(accountDto);
        AccountDto expectedDto = testDataStorage.getAccountDto(true, false);
        when(accountService.createAccount(accountDto)).thenReturn(expectedDto);

        //when
        String actualDtoJson = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountDtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        AccountDto actualDto = objectMapper.readValue(actualDtoJson, AccountDto.class);

        //then
        assertEquals(expectedDto, actualDto);
        verify(accountService).createAccount(accountDto);
    }

    @Test
    void createAccount_WhenInvalidInput_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        AccountDto invalidAccountDto = testDataStorage.getAccountDto(false, true);
        String invalidAccountDtoJson = objectMapper.writeValueAsString(invalidAccountDto);
        String expectedMessage = "Currency code can not be empty.";

        //when
        String errorDataJson = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidAccountDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(accountService);
    }

    @Test
    void createAccount_WhenUserDoesNotExist_CatchUserNotFoundExceptionAndReturnsNoContent() throws Exception {
        //given
        AccountDto accountDto = testDataStorage.getAccountDto(true, true);
        String accountDtoJson = objectMapper.writeValueAsString(accountDto);
        String expectedMessage = "There is no client with id = " + accountDto.getClientId();
        when(accountService.createAccount(accountDto)).thenThrow(new EntityNotFoundException(expectedMessage));

        //when
        String errorDataJson = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountDtoJson))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verify(accountService).createAccount(accountDto);
    }

    @Test
    void deleteAccountById_WhenValidId_InvokeDeleteAccountById() throws Exception {
        //given
        String accountId = "1";

        //when
        mockMvc.perform(delete("/account/delete/{accountId}", accountId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        verify(accountService).deleteAccountById(Long.valueOf(accountId));
    }

    @Test
    void deleteAccountById_WhenInvalidId_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        String invalidAccountId = "-1";
        String expectedMessage = "deleteAccountById.accountId: must be greater than 0";

        //when
        String errorDataJson = mockMvc.perform(delete("/account/delete/{accountId}", invalidAccountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(accountService);
    }

    @Test
    void findActiveAccountsWhereProductIdIs_WhenValidId_InvokeFindActiveAccountsWhereProductIdIsAndReturnsIsOk() throws Exception {
        //given
        String productId = "1";
        List<AccountDto> expectedList = List.of(testDataStorage.getAccountDto(true, false));
        when(accountService.getActiveAccountsWhereProductIdIs(Long.valueOf(productId))).thenReturn(expectedList);

        //when
        String dtoListJson = mockMvc.perform(get("/account/active/with-product/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<AccountDto> actualList = objectMapper.readValue(dtoListJson, new TypeReference<>() {});

        //then
        assertEquals(expectedList, actualList);
        verify(accountService).getActiveAccountsWhereProductIdIs(Long.valueOf(productId));
    }

    @Test
    void findActiveAccountsWhereProductIdIs_WhenInvalidId_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        String invalidProductId = "-1";
        String expectedMessage = "findActiveAccountsWhereProductIdIs.productId: must be greater than 0";

        //when
        String errorDataJson = mockMvc.perform(get("/account/active/with-product/{productId}", invalidProductId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(accountService);
    }
}