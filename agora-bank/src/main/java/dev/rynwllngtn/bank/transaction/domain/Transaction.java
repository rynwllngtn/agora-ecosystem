package dev.rynwllngtn.bank.transaction.domain;

import dev.rynwllngtn.common.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@EntityListeners(value = AuditingEntityListener.class)
@Entity @Table(name = "transactions")
public class Transaction {

    @Id @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "correlation_id", nullable = false)
    private UUID correlationId;

    @Column(name = "amount", nullable = false, precision = 9, scale = 2)
    private BigDecimal amount;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @CreatedDate
    private LocalDateTime createdAt;

    private Transaction(UUID accountId, UUID correlationId, BigDecimal amount, TransactionType type) {
        id = UUID.randomUUID();
        this.accountId = accountId;
        this.correlationId = correlationId;
        this.amount = amount;
        this.type = type;
    }

    public static Transaction getDebitInstance(UUID accountId, UUID correlationId, BigDecimal amount) {
        return new Transaction(accountId, correlationId, amount, TransactionType.DEBIT);
    }

    public static Transaction getCreditInstance(UUID accountId, UUID correlationId, BigDecimal amount) {
        return new Transaction(accountId, correlationId, amount, TransactionType.CREDIT);
    }

}