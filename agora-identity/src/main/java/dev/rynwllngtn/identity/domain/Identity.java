package dev.rynwllngtn.identity.domain;

import dev.rynwllngtn.common.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter @NoArgsConstructor @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@EntityListeners(value = AuditingEntityListener.class)
@Entity @Table(name = "identities")
public class Identity extends AuditableEntity {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "status", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private IdentityStatus status;

    public Identity(String cpf, String email, String password) {
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.status = IdentityStatus.ACTIVE;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void activate() {
        status = IdentityStatus.ACTIVE;
    }

    public void deactivate() {
        status = IdentityStatus.DEACTIVATED;
    }

    public void suspend() {
        status = IdentityStatus.SUSPENDED;
    }

}