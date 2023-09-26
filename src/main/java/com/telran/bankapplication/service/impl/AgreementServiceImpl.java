package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.repository.AgreementRepository;
import com.telran.bankapplication.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgreementServiceImpl implements AgreementService {

    private final AgreementRepository agreementRepository;

    @Override
    public void deleteAgreementById(Long agreementId) {
        agreementRepository.deleteById(agreementId);
    }
}
