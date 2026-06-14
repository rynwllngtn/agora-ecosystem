package dev.rynwllngtn.identity.application.service;

import dev.rynwllngtn.identity.application.dto.IdentityResponseDto;

import java.util.UUID;

public interface IdentityService {

    IdentityResponseDto findById(UUID id);

}