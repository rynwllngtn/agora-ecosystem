package dev.rynwllngtn.bank.account.application.service;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.application.mapper.AccountMapper;
import dev.rynwllngtn.bank.account.domain.Account;
import dev.rynwllngtn.bank.account.domain.AccountDetails;
import dev.rynwllngtn.bank.account.domain.AccountRepository;
import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountServiceImplementation implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private Account findByIdOrThrow(UUID id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(
                () -> new ResourceNotFoundException("Account não encontrada!")
        );
    }

    private AccountDetails newAccountDetails() {
        Long generated = accountRepository.nextAccountNumberFromSequence();
        String number = String.format("%08d", generated);
        return new AccountDetails(number);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto findById(UUID id) {
        Account account = findByIdOrThrow(id);
        return accountMapper.toResponseDto(account);
    }

    @Override
    @Transactional
    public void create(UUID customerId) {
        Account account = new Account(customerId, newAccountDetails());
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountResponseDto activate(UUID id) {
        Account account = findByIdOrThrow(id);
        account.activate();
        accountRepository.save(account);
        return accountMapper.toResponseDto(account);
    }

    @Override
    @Transactional
    public AccountResponseDto inactivate(UUID id) {
        Account account = findByIdOrThrow(id);
        account.inactivate();
        accountRepository.save(account);
        return accountMapper.toResponseDto(account);
    }

    @Override
    @Transactional
    public AccountResponseDto suspend(UUID id) {
        Account account = findByIdOrThrow(id);
        account.suspend();
        accountRepository.save(account);
        return accountMapper.toResponseDto(account);
    }

}