package dev.rynwllngtn.bank.account.infrastructure.persistance;

import dev.rynwllngtn.bank.account.domain.Account;
import dev.rynwllngtn.bank.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AccountRepositoryAdapter implements AccountRepository {

    private final AccountRepositoryJpa accountRepository;

    @Override
    public Optional<Account> findById(UUID id) {
        return accountRepository.findById(id);
    }

    @Override
    public boolean existsByCustomerId(UUID customerId) {
        return accountRepository.existsByCustomerId(customerId);
    }

    @Override
    public Long nextAccountNumberFromSequence() {
        return accountRepository.nextAccountNumberFromSequence();
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

}