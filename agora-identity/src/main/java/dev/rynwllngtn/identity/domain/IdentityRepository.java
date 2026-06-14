package dev.rynwllngtn.identity.domain;

import java.util.Optional;
import java.util.UUID;

public interface IdentityRepository {

    Optional<Identity> findById(UUID id);

}