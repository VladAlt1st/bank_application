package com.telran.bankapplication.data;

import com.telran.bankapplication.dto.*;
import com.telran.bankapplication.entity.*;
import com.telran.bankapplication.entity.enums.*;
import com.telran.bankapplication.mapper.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class TestDataStorage {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private final AgreementMapper agreementMapper = Mappers.getMapper(AgreementMapper.class);

    public UserDto getUserDto(boolean isValid, boolean isRequest) {
        return new UserDto(
                isRequest ? null : "1",
                isValid ? "CLIENT" : "client",
                isRequest ? null : "ACTIVE",
                isValid ? "Adam" : " ",
                "Smith",
                "adamS@gmail.com",
                isRequest ? "paSsWord1@" : null,
                "8 Hazelcrest Center",
                "+48 917-342-358",
                isRequest ? null : String.valueOf(ZonedDateTime.now()),
                isRequest ? null : String.valueOf(ZonedDateTime.now())
        );
    }

    public User getUser() {
        return userMapper.toEntity(getUserDto(true, true));
    }

    public AccountDto getAccountDto(boolean isValid, boolean isRequest) {
        return new AccountDto(
                isRequest ? null : "1",
                "1",
                isRequest ? null : "1234221133224321",
                isRequest ? null : "ACTIVE",
                isRequest ? null : "0",
                isValid ? "USD" : null,
                isRequest ? null : String.valueOf(ZonedDateTime.now()),
                isRequest ? null : String.valueOf(ZonedDateTime.now())
        );
    }

    public Account getAccount() {
        return accountMapper.toEntity(getAccountDto(true, true));
    }

    public TransactionDto getTransactionDto(boolean isValid, boolean isRequest) {
        return new TransactionDto(
                isRequest ? null : "1",
                isValid ? "1122332244331111" : "11223a22441111",
                "1122332244331112",
                isRequest ? null : "COMPLETED",
                "TRANSFER",
                "200",
                "Some transfer.",
                isRequest ? null : String.valueOf(ZonedDateTime.now())
        );
    }

    public Transaction getTransaction() {
        return transactionMapper.toEntity(getTransactionDto(true, true));
    }

    public ProductDto getProductDto() {
        return productMapper.toDto(getProduct());
    }

    public Product getProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("credit");
        product.setType(ProductType.CREDIT);
        product.setStatus(ProductStatus.AVAILABLE);
        product.setCurrencyCode(CurrencyCode.USD);
        product.setInterestRate(new BigDecimal(8));
        product.setProductLimit(new BigDecimal(10000));
        product.setCreatedAt(ZonedDateTime.now());
        product.setUpdatedAt(ZonedDateTime.now());
        return product;
    }

    public AgreementDto getAgreementDto() {
        return agreementMapper.toDto(getAgreement());
    }

    public Agreement getAgreement() {
        Agreement agreement = new Agreement();
        agreement.setId(1L);
        Account account = getAccount();
        account.setId(1L);
        agreement.setAccount(account);
        User manager = getUser();
        manager.setId(1L);
        manager.setRole(Role.MANAGER);
        agreement.setManager(manager);
        Product product = getProduct();
        product.setId(1L);
        agreement.setProduct(product);
        agreement.setStatus(AgreementStatus.APPROVED);
        agreement.setSum(new BigDecimal(10000));
        agreement.setCreatedAt(ZonedDateTime.now());
        agreement.setUpdatedAt(ZonedDateTime.now());
        return agreement;
    }
}
