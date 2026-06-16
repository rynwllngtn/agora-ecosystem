package dev.rynwllngtn.identity.application.dto;

public record IdentityCreateRequestDto(
        String cpf,
        String email,
        String password
) {}