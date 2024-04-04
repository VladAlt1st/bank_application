package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
