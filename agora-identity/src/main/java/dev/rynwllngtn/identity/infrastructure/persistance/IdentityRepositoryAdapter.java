package dev.rynwllngtn.identity.infrastructure.persistance;

import dev.rynwllngtn.identity.domain.Identity;
import dev.rynwllngtn.identity.domain.IdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class IdentityRepositoryAdapter implements IdentityRepository {

    private final IdentityRepositoryJpa identityRepository;

    @Override
    public Optional<Identity> findById(UUID id) {
        return identityRepository.findById(id);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return identityRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByEmail(String email) {
        return identityRepository.existsByEmail(email);
    }

    @Override
    public Identity save(Identity identity) {
        return identityRepository.save(identity);
    }

}