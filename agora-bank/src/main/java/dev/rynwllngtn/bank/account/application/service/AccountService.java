package dev.rynwllngtn.bank.account.application.service;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {

    AccountResponseDto findById(UUID id);

    void create(UUID customerId);

    AccountResponseDto deposit(UUID id, BigDecimal amount);
    AccountResponseDto withdraw(UUID id, BigDecimal amount);

    AccountResponseDto activate(UUID id);
    AccountResponseDto deactivate(UUID id);
    AccountResponseDto suspend(UUID id);

}