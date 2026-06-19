package dev.rynwllngtn.identity.domain;

import java.util.Optional;
import java.util.UUID;

public interface IdentityRepository {

    Optional<Identity> findById(UUID id);

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    Identity save(Identity identity);

}