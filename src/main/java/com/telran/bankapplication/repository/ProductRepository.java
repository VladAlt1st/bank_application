package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findProductById(Long id);
}
