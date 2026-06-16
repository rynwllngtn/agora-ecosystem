package dev.rynwllngtn.identity.application.dto;

public record IdentityUpdateEmailRequestDto(
        String newEmail,
        String password
) {}