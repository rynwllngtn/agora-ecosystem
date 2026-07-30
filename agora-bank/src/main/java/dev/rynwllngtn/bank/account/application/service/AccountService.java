package dev.rynwllngtn.bank.account.application.service;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;

import java.util.UUID;

public interface AccountService {

    AccountResponseDto findById(UUID id);

    void create(UUID customerId);

    AccountResponseDto activate(UUID id);
    AccountResponseDto deactivate(UUID id);
    AccountResponseDto suspend(UUID id);

}