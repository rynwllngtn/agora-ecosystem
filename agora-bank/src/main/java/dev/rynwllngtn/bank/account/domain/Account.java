package dev.rynwllngtn.bank.account.domain;

import dev.rynwllngtn.bank.account.domain.exception.InactiveAccountException;
import dev.rynwllngtn.bank.account.domain.exception.InsufficientFundsException;
import dev.rynwllngtn.bank.account.domain.exception.InvalidAmountException;
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

    public void deposit(BigDecimal amount) {
        validateActiveAccount();
        validateAmount(amount);
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateActiveAccount();
        validateAmount(amount);
        validateSufficientFunds(amount);
        balance = balance.subtract(amount);
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = AccountStatus.INACTIVE;
    }

    public void suspend() {
        this.status = AccountStatus.SUSPENDED;
    }

    private void validateActiveAccount() {
        if (status != AccountStatus.ACTIVE) {
            throw new InactiveAccountException("A conta precisa estar ativa para realizar essa operação");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Valor inválido para esta operação");
        }
    }

    private void validateSufficientFunds(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente");
        }
    }

}