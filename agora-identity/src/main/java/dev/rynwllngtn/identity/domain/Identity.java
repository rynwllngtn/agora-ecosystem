package dev.rynwllngtn.identity.domain;

import dev.rynwllngtn.common.domain.AuditableEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter @NoArgsConstructor @EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity @Table(name = "identities")
public class Identity extends AuditableEntity {

    @Id @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private IdentityStatus status;

    public Identity(String cpf, String password, String email) {
        this.cpf = cpf;
        this.password = password;
        this.email = email;
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