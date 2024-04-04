package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.dto.AgreementDto;
import com.telran.bankapplication.entity.enums.ProductType;
import com.telran.bankapplication.mapper.AgreementMapper;
import com.telran.bankapplication.repository.AgreementRepository;
import com.telran.bankapplication.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;
    private final AgreementMapper agreementMapper;

    @Override
    public List<AgreementDto> getAllAgreementsWhereProductTypeIs(ProductType productType) {
        return agreementMapper.toDtos(agreementRepository.getAllAgreementsWhereProductTypeIs(productType));
    }
}
