package dev.rynwllngtn.bank.account.application.dto;

import dev.rynwllngtn.bank.account.domain.AccountStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        UUID customerId,
        String agency,
        String number,
        String bankCode,
        AccountStatus status,
        BigDecimal balance
) {}