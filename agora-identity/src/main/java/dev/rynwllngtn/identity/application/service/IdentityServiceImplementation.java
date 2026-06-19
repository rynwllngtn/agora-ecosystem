package dev.rynwllngtn.identity.application.service;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;
import dev.rynwllngtn.identity.application.exception.DuplicateResourceException;
import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.identity.application.exception.WrongPasswordException;
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
        if (identityRepository.existsByCpf(createRequestDto.cpf())) {
            throw new DuplicateResourceException("Esse cpf já está em uso!");
        }
        if (identityRepository.existsByEmail(createRequestDto.email())) {
            throw new DuplicateResourceException("Esse email já está em uso!");
        }
        Identity identity = identityMapper.toEntity(createRequestDto);
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto changePassword(UUID id, IdentityUpdatePasswordRequestDto updateRequestDto) {
        if (!updateRequestDto.doesNewPasswordMatch()) {
            throw new WrongPasswordException("As senhas não conferem!");
        }
        Identity identity = findByIdOrThrow(id);
        if (!identity.getPassword().equals(updateRequestDto.oldPassword())) {
            throw new WrongPasswordException("A senha não confere com a atual!");
        }
        identity.changePassword(updateRequestDto.newPassword());
        identity = identityRepository.save(identity);
        return identityMapper.toResponseDto(identity);
    }

    @Override
    @Transactional
    public IdentityResponseDto changeEmail(UUID id, IdentityUpdateEmailRequestDto updateRequestDto) {
        if (identityRepository.existsByEmail(updateRequestDto.newEmail())) {
            throw new DuplicateResourceException("Esse email já está em uso!");
        }
        Identity identity = findByIdOrThrow(id);
        if (!identity.getPassword().equals(updateRequestDto.password())) {
            throw new WrongPasswordException("A senha não confere com a atual!");
        }
        identity.changeEmail(updateRequestDto.newEmail());
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