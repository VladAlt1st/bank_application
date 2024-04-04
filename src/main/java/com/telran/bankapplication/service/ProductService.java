package com.telran.bankapplication.service;

import com.telran.bankapplication.dto.ProductDto;

public interface ProductService {

    ProductDto getProductById(Long productId);
}
