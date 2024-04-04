package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Transaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where t.debitAccount.accountNumber = :number or t.creditAccount.accountNumber = :number")
    List<Transaction> getAllTransactionWhereAccountNumberIs(@Param("number") String number);
}
