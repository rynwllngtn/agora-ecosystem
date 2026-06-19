package dev.rynwllngtn.identity.infrastructure.persistance;

import dev.rynwllngtn.identity.domain.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IdentityRepositoryJpa extends JpaRepository<Identity, UUID> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

}