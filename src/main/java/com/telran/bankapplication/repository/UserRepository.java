package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByTaxCode(String taxCode);
}
