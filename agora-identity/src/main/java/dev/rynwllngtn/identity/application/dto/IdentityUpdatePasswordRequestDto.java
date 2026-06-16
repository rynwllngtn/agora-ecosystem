package dev.rynwllngtn.identity.application.dto;

public record IdentityUpdatePasswordRequestDto(
        String newPassword,
        String password
) {}