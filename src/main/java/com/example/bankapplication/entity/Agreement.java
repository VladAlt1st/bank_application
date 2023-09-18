package com.example.bankapplication.entity;

import com.example.bankapplication.entity.enums.AgreementStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AgreementStatus status;

    @Column(name = "sum")
    private BigDecimal sum;

    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAd;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAd;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @ManyToOne()
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agreement agreement = (Agreement) o;
        return Objects.equals(id, agreement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Agreement{" +
                "id=" + id +
                ", status=" + status +
                ", sum=" + sum +
                ", createdAd=" + createdAd +
                ", updatedAd=" + updatedAd +
                ", manager=" + manager +
                ", product=" + product +
                '}';
    }
}
