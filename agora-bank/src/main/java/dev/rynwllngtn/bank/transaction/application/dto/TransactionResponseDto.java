package dev.rynwllngtn.bank.transaction.application.dto;

import dev.rynwllngtn.bank.transaction.domain.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponseDto(
        UUID id,
        UUID accountId,
        UUID correlationId,
        BigDecimal amount,
        TransactionType type
) {}