package dev.rynwllngtn.bank.transaction.infrastructure.persistence;

import dev.rynwllngtn.bank.transaction.domain.Transaction;
import dev.rynwllngtn.bank.transaction.domain.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionRepositoryJpa transactionRepository;

    @Override
    public Optional<Transaction> findById(UUID id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}