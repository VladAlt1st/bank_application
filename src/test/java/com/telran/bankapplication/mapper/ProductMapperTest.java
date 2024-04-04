package com.telran.bankapplication.mapper;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.ProductDto;
import com.telran.bankapplication.entity.Product;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void toDto_WhenValidEntity_Successful() {
        //given
        Product product = testDataStorage.getProduct();

        //when
        ProductDto productDto = productMapper.toDto(product);

        //then
        assertEquals(product.getId().toString(), productDto.getId());
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getType().toString(), productDto.getType());
        assertEquals(product.getStatus().toString(), productDto.getStatus());
        assertEquals(product.getCurrencyCode().toString(), productDto.getCurrencyCode());
        assertEquals(product.getInterestRate().toString(), productDto.getInterestRate());
        assertEquals(product.getProductLimit().toString(), productDto.getProductLimit());
        assertEquals(product.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), productDto.getCreatedAt());
        assertEquals(product.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), productDto.getUpdatedAt());
    }
}