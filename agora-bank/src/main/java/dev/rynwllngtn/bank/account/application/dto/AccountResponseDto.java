package dev.rynwllngtn.bank.account.application.dto;

import dev.rynwllngtn.bank.account.domain.AccountDetails;
import dev.rynwllngtn.bank.account.domain.AccountStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDto(
        UUID id,
        UUID customerId,
        AccountDetails details,
        AccountStatus status,
        BigDecimal balance
) {}