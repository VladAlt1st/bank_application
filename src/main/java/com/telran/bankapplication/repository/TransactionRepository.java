package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
