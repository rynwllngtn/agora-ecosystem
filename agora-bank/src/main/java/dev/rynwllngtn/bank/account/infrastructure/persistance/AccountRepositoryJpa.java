package dev.rynwllngtn.bank.account.infrastructure.persistance;

import dev.rynwllngtn.bank.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepositoryJpa extends JpaRepository<Account, UUID> {
}