package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.ProductDto;
import com.telran.bankapplication.service.ProductService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable(name = "productId") @Positive Long productId) {
        return productService.getProductById(productId);
    }
}
