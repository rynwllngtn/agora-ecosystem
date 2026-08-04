package dev.rynwllngtn.bank.transaction.domain;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

    Optional<Transaction> findById(UUID id);

    Transaction save(Transaction transaction);

}