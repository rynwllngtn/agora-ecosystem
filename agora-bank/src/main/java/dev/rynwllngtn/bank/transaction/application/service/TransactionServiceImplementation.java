package dev.rynwllngtn.bank.transaction.application.service;

import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.application.mapper.TransactionMapper;
import dev.rynwllngtn.bank.transaction.domain.Transaction;
import dev.rynwllngtn.bank.transaction.domain.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    private Transaction findByIdOrThrow(UUID id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.orElseThrow(
                () -> new ResourceNotFoundException("Transaction não encontrada!")
        );
    }

    @Override
    public TransactionResponseDto findById(UUID id) {
        Transaction transaction = findByIdOrThrow(id);
        return transactionMapper.toResponseDto(transaction);
    }

    @Override
    public void debit(UUID accountId, BigDecimal amount) {
        UUID correlationId = UUID.randomUUID();
        Transaction transaction = Transaction.getDebitInstance(
                accountId, correlationId, amount
        );
        transactionRepository.save(transaction);
    }

    @Override
    public void credit(UUID accountId, BigDecimal amount) {
        UUID correlationId = UUID.randomUUID();
        Transaction transaction = Transaction.getCreditInstance(
                accountId, correlationId, amount
        );
        transactionRepository.save(transaction);
    }

}