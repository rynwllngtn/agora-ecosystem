package dev.rynwllngtn.bank.operation.withdraw.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WithdrawResponseDto(
        UUID accountId,
        UUID transactionId,
        UUID correlationId,
        BigDecimal amount,
        BigDecimal newBalance,
        LocalDateTime timestamp
) {}