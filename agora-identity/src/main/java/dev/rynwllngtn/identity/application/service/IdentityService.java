package dev.rynwllngtn.identity.application.service;

import dev.rynwllngtn.identity.application.dto.IdentityCreateRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdateEmailRequestDto;
import dev.rynwllngtn.identity.application.dto.IdentityUpdatePasswordRequestDto;

import java.util.UUID;

public interface IdentityService {

    IdentityResponseDto findById(UUID id);

    IdentityResponseDto create(IdentityCreateRequestDto createRequestDto);

    IdentityResponseDto changePassword(UUID id, IdentityUpdatePasswordRequestDto updateRequestDto);
    IdentityResponseDto changeEmail(UUID id, IdentityUpdateEmailRequestDto updateRequestDto);

    IdentityResponseDto activate(UUID id);
    IdentityResponseDto deactivate(UUID id);
    IdentityResponseDto suspend(UUID id);

}