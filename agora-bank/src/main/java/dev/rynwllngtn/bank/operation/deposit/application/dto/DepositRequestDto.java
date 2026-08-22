package dev.rynwllngtn.bank.operation.deposit.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositRequestDto(
        @NotNull(message = "O ID da conta não pode ser nulo")
        UUID accountId,
        @NotNull(message = "O valor não pode ser nulo")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal amount
) {}