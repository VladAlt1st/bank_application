package com.telran.bankapplication.controller;

import com.telran.bankapplication.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agreements")
@RequiredArgsConstructor
public class AgreementController {

    private final AgreementService agreementService;

    @PostMapping("/delete/{agreementId}")
    public void deleteAgreementById(@PathVariable Long agreementId) {
        agreementService.deleteAgreementById(agreementId);
    }
}
