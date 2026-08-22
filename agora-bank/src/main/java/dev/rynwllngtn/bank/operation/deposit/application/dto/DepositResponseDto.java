package dev.rynwllngtn.bank.operation.deposit.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DepositResponseDto(
        UUID accountId,
        UUID transactionId,
        UUID correlationId,
        BigDecimal amount,
        BigDecimal newBalance,
        LocalDateTime timestamp
) {}