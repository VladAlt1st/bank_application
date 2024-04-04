package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Account;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.status = 'ACTIVE' and a.agreement.product.id = :productId")
    List<Account> getActiveAccountsWhereProductIdIs(@Param("productId") Long productId);

    Optional<Account> getAccountByAccountNumber(String number);

    @Query("select count(a) > 0 from Account a where a.accountNumber = :number")
    boolean isAccountNumberExists(@Param("number") String number);
}
