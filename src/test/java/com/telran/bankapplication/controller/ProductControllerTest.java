package com.telran.bankapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.*;
import com.telran.bankapplication.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void getProductById_WhenValidProductId_Successful() throws Exception {
        //given
        String productId = "1";
        ProductDto expectedDto = testDataStorage.getProductDto();
        when(productService.getProductById(Long.valueOf(productId))).thenReturn(expectedDto);

        //when
        String dtoJson = mockMvc.perform(get("/product/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ProductDto actualDto = objectMapper.readValue(dtoJson, ProductDto.class);

        //then
        assertEquals(expectedDto, actualDto);
        verify(productService).getProductById(Long.valueOf(productId));
    }

    @Test
    void getProductById_WhenInvalidProductId_CatchValidationExceptionAndReturnsBadRequest() throws Exception {
        //given
        String productId = "-1";
        String expectedMessage = "getProductById.productId: must be greater than 0";

        //when
        String errorDataJson = mockMvc.perform(get("/product/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        ErrorData errorData = objectMapper.readValue(errorDataJson, ErrorData.class);
        String actualMessage = errorData.getMessage();

        //then
        assertEquals(expectedMessage, actualMessage);
        verifyNoInteractions(productService);
    }
}