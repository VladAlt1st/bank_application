package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.dto.ProductDto;
import com.telran.bankapplication.mapper.ProductMapper;
import com.telran.bankapplication.repository.ProductRepository;
import com.telran.bankapplication.service.ProductService;
import com.telran.bankapplication.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto getProductById(Long productId) {
        return productMapper.toDto(productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product with id %s not found.", productId)))
        );
    }
}
