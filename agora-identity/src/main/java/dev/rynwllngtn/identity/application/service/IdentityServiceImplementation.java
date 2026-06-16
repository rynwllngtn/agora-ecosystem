package dev.rynwllngtn.identity.application.service;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.identity.application.mapper.IdentityMapper;
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

    @Transactional(readOnly = true)
    private Identity findByIdOrThrow(UUID id) {
        Optional<Identity> optionalIdentity = identityRepository.findById(id);
        return optionalIdentity.orElseThrow(
                () -> new ResourceNotFoundException("Identity não encontrada!")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public IdentityResponseDto findById(UUID id) {
        Identity identity = findByIdOrThrow(id);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto create(IdentityCreateRequestDto createRequestDto) {
        Identity identity = identityMapper.toEntity(createRequestDto);
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto changePassword(UUID id, IdentityUpdatePasswordRequestDto updateRequestDto) {
        Identity identity = findByIdOrThrow(id);
        identity.changePassword(updateRequestDto.newPassword());
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto changeEmail(UUID id, IdentityUpdateEmailRequestDto updateRequestDto) {
        Identity identity = findByIdOrThrow(id);
        identity.changePassword(updateRequestDto.newEmail());
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto activate(UUID id) {
        Identity identity = findByIdOrThrow(id);
        identity.activate();
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto deactivate(UUID id) {
        Identity identity = findByIdOrThrow(id);
        identity.deactivate();
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto suspend(UUID id) {
        Identity identity = findByIdOrThrow(id);
        identity.suspend();
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

}