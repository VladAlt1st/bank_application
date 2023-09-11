package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.AccountStatus;
import com.example.bankapplication.entity.enums.AccountType;
import com.example.bankapplication.entity.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    //возможно убрать
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "balance")
    private BigDecimal balance;

    //возможно убрать
    @Column(name = "currency_code")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User client;

    @OneToOne(
            mappedBy = "account", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL
    )
    private Agreement agreement;

    @OneToMany(
            mappedBy = "debitAccount", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL
    )
    private List<Transaction> debitTransactions;

    @OneToMany(
            mappedBy = "creditAccount", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL
    )
    private List<Transaction> creditTransactions;

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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", balance=" + balance +
                ", currencyCode=" + currencyCode +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", client=" + client +
                ", agreement=" + agreement +
                '}';
    }
}
