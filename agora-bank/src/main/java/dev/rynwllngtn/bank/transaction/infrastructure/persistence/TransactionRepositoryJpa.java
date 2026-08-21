package dev.rynwllngtn.bank.transaction.infrastructure.persistence;

import dev.rynwllngtn.bank.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepositoryJpa extends JpaRepository<Transaction, UUID> {
}