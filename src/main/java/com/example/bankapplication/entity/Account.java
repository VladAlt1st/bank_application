package com.example.bankapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    private Long id;
    private Long clientId;
    private String name;
    private int type;
    private int status;
    private long balance;
    private int currencyCode;
    private LocalDateTime createdAd;
    private LocalDateTime updatedAd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
