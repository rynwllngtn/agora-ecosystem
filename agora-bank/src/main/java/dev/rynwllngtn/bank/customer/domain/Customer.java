package dev.rynwllngtn.bank.customer.domain;

import dev.rynwllngtn.common.domain.TimestampedEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter @NoArgsConstructor @EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@EntityListeners(value = AuditingEntityListener.class)
@Entity @Table(name = "customers")
public class Customer extends TimestampedEntity {

    @Id @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "identity_id", nullable = false, unique = true)
    private UUID identityId;

    @Column(name = "status", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private CustomerStatus status;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public Customer(UUID identityId, String cpf, String email) {
        this.identityId = identityId;
        this.cpf = cpf;
        this.email = email;
    }

}