package dev.rynwllngtn.bank.transaction.application.service;

import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDto findById(UUID id);

    TransactionResponseDto debit(UUID accountId, UUID correlationId, BigDecimal amount);
    TransactionResponseDto credit(UUID accountId, UUID correlationId, BigDecimal amount);

}