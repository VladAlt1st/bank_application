package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.AgreementDto;
import com.telran.bankapplication.entity.enums.ProductType;
import com.telran.bankapplication.service.AgreementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/agreement")
@RequiredArgsConstructor
public class AgreementController {

    private final AgreementService agreementService;

    @GetMapping("/all/{productType}")
    @ResponseStatus(HttpStatus.OK)
    public List<AgreementDto> getAllAgreementsWhereProductTypeIs(@PathVariable("productType") ProductType productType) {
        return agreementService.getAllAgreementsWhereProductTypeIs(productType);
    }
}
