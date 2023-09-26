package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Agreement;
import org.springframework.data.repository.CrudRepository;

public interface AgreementRepository extends CrudRepository<Agreement, Long> {
}
