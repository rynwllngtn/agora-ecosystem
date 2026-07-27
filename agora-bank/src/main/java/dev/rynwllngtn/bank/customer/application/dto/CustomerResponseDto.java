package dev.rynwllngtn.bank.customer.application.dto;

import dev.rynwllngtn.bank.customer.domain.CustomerStatus;

import java.util.UUID;

public record CustomerResponseDto(
        UUID id,
        String email,
        CustomerStatus status
) {}