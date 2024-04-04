package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.Agreement;
import com.telran.bankapplication.entity.enums.ProductType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    @Query("select a from Agreement a where a.product.type = :productType")
    List<Agreement> getAllAgreementsWhereProductTypeIs(@Param("productType") ProductType productType);
}
