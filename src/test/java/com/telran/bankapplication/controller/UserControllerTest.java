package com.telran.bankapplication.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.*;
import com.telran.bankapplication.entity.enums.UserStatus;
import com.telran.bankapplication.service.UserService;
import com.telran.bankapplication.service.exception.UserAlreadyExistsException;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void createUser_WhenValidInput_Successful() throws Exception {
        //given
        UserDto userDto = testDataStorage.getUserDto(true, true);
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        when(userService.createUser(userDto)).thenReturn(1L);

        //when
        String actualId = mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        assertEquals("1", actualId);
        verify(userService).createUser(userDto);
    }

    @Test
    void createUser_WhenInvalidInput_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        UserDto invalidUserDto = testDataStorage.getUserDto(false, true);
        String invalidUserDtoJson = objectMapper.writeValueAsString(invalidUserDto);
        String expectedMessage = "First name must contain only letters of the English alphabet.";

        //when
        String errorDataJson = mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(userService);
    }

    @Test
    void createUser_WhenUserAlreadyExists_CatchUserAlreadyExistsExceptionAndReturnsBadRequest() throws Exception {
        //given
        UserDto userDto = testDataStorage.getUserDto(true, true);
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        String expectedMessage = String.format("User with email %s already exists.", userDto.getEmail());
        when(userService.createUser(userDto)).thenThrow(new UserAlreadyExistsException(expectedMessage));

        //when
        String errorDataJson = mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verify(userService).createUser(userDto);
    }

    @Test
    void createUser_WhenUserStatusInvalid_CatchIllegalArgumentExceptionAndReturnsBadRequest() throws Exception {
        //given
        UserDto userDto = testDataStorage.getUserDto(true, true);
        String userDtoJson = objectMapper.writeValueAsString(userDto);
        String expectedMessage = "No enum constant com.telran.bankapplication.entity.enums.Role.CLIEN";
        when(userService.createUser(userDto)).thenThrow(new IllegalArgumentException(expectedMessage));

        //when
        String errorDataJson = mockMvc.perform(post("/user/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verify(userService).createUser(userDto);
    }

    @Test
    void getAllClientsWhereStatusIs_WhenValidStatus_Successful() throws Exception {
        //given
        String status = UserStatus.ACTIVE.toString();
        List<UserDto> expectedList = List.of(testDataStorage.getUserDto(true, false));
        when(userService.getClientsWhereStatusIs(UserStatus.valueOf(status))).thenReturn(expectedList);

        //when
        String dtoListJson = mockMvc.perform(get("/user/client/all/{status}", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<UserDto> actualList = objectMapper.readValue(dtoListJson, new TypeReference<>() {});

        //then
        assertEquals(expectedList, actualList);
        verify(userService).getClientsWhereStatusIs(UserStatus.valueOf(status));
    }

    @Test
    void getAllClientsWhereStatusIs_WhenInvalidStatus_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        String invalidStatus = "ACTIV";
        String expectedMessage = "No enum constant com.telran.bankapplication.entity.enums.UserStatus.ACTIV";

        //when
        String errorDataJson = mockMvc.perform(get("/user/client/all/{status}", invalidStatus)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(userService);
    }
}