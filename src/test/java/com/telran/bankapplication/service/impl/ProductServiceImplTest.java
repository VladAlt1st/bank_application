package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.ProductDto;
import com.telran.bankapplication.entity.Product;
import com.telran.bankapplication.mapper.ProductMapper;
import com.telran.bankapplication.repository.ProductRepository;
import com.telran.bankapplication.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void getProductById_WhenProductExists_Successful() {
        //given
        Long productId = 1L;
        Product product = testDataStorage.getProduct();
        ProductDto expectedDto = testDataStorage.getProductDto();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(expectedDto);

        //when
        ProductDto actualDto = productService.getProductById(productId);

        //then
        assertEquals(expectedDto, actualDto);
        verify(productRepository).findById(productId);
        verify(productMapper).toDto(product);
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ThrowEntityNotFountException() {
        //given
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        //when then
        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(productId));
    }
}