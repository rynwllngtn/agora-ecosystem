package dev.rynwllngtn.bank.transaction.application.service;

import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {

    TransactionResponseDto findById(UUID id);

    void debit(UUID accountId, BigDecimal amount);
    void credit(UUID accountId, BigDecimal amount);

}