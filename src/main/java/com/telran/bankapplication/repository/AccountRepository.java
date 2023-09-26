package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
