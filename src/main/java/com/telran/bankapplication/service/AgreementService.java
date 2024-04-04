package com.telran.bankapplication.service;

import com.telran.bankapplication.dto.AgreementDto;
import com.telran.bankapplication.entity.enums.ProductType;

import java.util.List;

public interface AgreementService {

    List<AgreementDto> getAllAgreementsWhereProductTypeIs(ProductType productType);
}
