package dev.rynwllngtn.bank.account.domain;

import dev.rynwllngtn.common.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@EntityListeners(value = AuditingEntityListener.class)
@Entity @Table(name = "accounts")
public class Account extends AuditableEntity {

    @Id @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "customer_id", nullable = false, unique = true)
    private UUID customerId;

    @Embedded
    @Column(name = "details", nullable = false)
    private AccountDetails details;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @Column(name = "balance", nullable = false, precision = 9, scale = 2)
    private BigDecimal balance;

    public Account(UUID customerId, AccountDetails details) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.details = details;
        this.status = AccountStatus.ACTIVE;
        this.balance = BigDecimal.ZERO;
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

    public void inactivate() {
        this.status = AccountStatus.INACTIVE;
    }

    public void suspend() {
        this.status = AccountStatus.SUSPENDED;
    }

}