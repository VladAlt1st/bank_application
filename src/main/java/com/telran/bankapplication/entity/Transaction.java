package com.telran.bankapplication.entity;

import com.telran.bankapplication.entity.enums.TransactionStatus;
import com.telran.bankapplication.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "debit_account_id", referencedColumnName = "id")
    private Account debitAccount;

    @ManyToOne(/*fetch = FetchType.LAZY*/)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "id")
    private Account creditAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", debitAccount=" + debitAccount +
                ", creditAccount=" + creditAccount +
                '}';
    }
}


