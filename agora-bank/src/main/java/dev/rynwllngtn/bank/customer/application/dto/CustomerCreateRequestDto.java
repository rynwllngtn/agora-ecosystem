package dev.rynwllngtn.bank.customer.application.dto;

import java.util.UUID;

public record CustomerCreateRequestDto(
        UUID identityId,
        String cpf,
        String email
) {}