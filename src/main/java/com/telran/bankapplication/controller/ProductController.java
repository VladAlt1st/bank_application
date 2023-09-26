package com.telran.bankapplication.controller;

import com.telran.bankapplication.entity.Product;
import com.telran.bankapplication.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable(name = "productId") Long productId) {
        return productService.getProductById(productId);
    }
}
