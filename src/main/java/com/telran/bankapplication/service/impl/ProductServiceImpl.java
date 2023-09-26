package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.entity.Product;
import com.telran.bankapplication.repository.ProductRepository;
import com.telran.bankapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    @Transactional
    public Product getProductById(Long productId) {
        return productRepository.findProductById(productId).orElseThrow(
                NoSuchElementException::new
        );
    }
}
