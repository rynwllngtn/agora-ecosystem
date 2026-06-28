package dev.rynwllngtn.bank.customer.application.dto;

import java.util.UUID;

public record CustomerResponseDto(
        UUID id,
        String cpf,
        String email
) {}