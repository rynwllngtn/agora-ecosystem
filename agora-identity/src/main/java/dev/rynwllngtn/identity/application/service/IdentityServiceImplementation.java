package dev.rynwllngtn.identity.application.service;

import dev.rynwllngtn.identity.application.mapper.IdentityMapper;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.identity.domain.Identity;
import dev.rynwllngtn.identity.domain.IdentityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class IdentityServiceImplementation implements IdentityService {

    private final IdentityRepository identityRepository;
    private final IdentityMapper identityMapper;

    @Override
    @Transactional(readOnly = true)
    public IdentityResponseDto findById(UUID id) {
        Optional<Identity> optionalIdentity = identityRepository.findById(id);
        Identity identity = optionalIdentity.orElseThrow(
                () -> new ResourceNotFoundException("Identity não encontrada!")
        );
        return identityMapper.toResponseDto(identity);
    }

}