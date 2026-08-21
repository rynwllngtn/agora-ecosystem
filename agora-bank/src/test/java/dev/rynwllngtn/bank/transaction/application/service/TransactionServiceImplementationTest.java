package dev.rynwllngtn.bank.transaction.application.service;

import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.application.mapper.TransactionMapper;
import dev.rynwllngtn.bank.transaction.builder.TransactionBuilder;
import dev.rynwllngtn.bank.transaction.domain.Transaction;
import dev.rynwllngtn.bank.transaction.domain.TransactionRepository;
import dev.rynwllngtn.bank.transaction.domain.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplementationTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImplementation transactionService;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    @Nested
    @DisplayName(value = "Testes de busca por ID")
    class FindByIdTest {

        @Test
        void shouldReturnTransactionResponseDto() {
            Transaction mockTransaction = TransactionBuilder.Entity.validOfType(TransactionType.CREDIT).build();
            TransactionResponseDto mockResponseDto = TransactionBuilder.Response.fromEntity(mockTransaction);
            UUID id = mockTransaction.getId();

            when(transactionRepository.findById(id)).thenReturn(Optional.of(mockTransaction));
            when(transactionMapper.toResponseDto(mockTransaction)).thenReturn(mockResponseDto);

            TransactionResponseDto result = transactionService.findById(id);

            assertNotNull(result);
            assertEquals(mockResponseDto.id(), result.id());
            assertEquals(mockResponseDto.accountId(), result.accountId());
            assertEquals(mockResponseDto.correlationId(), result.correlationId());
            assertEquals(mockResponseDto.amount(), result.amount());
            assertEquals(mockResponseDto.type(), result.type());

            verify(transactionRepository).findById(id);
            verify(transactionMapper).toResponseDto(mockTransaction);
        }

        @Test
        void shouldThrowResourceNotFoundException() {
            UUID nonExistentId = UUID.randomUUID();
            when(transactionRepository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> {
                transactionService.findById(nonExistentId);
            });

            verify(transactionRepository).findById(nonExistentId);
        }

    }

    @Nested
    @DisplayName(value = "Testes de débito")
    class DebitTests {

        @Test
        void shouldCreateAndSaveDebitTransactionSuccessfully() {
            UUID accountId = TransactionBuilder.defaultAccountId;
            UUID correlationId = TransactionBuilder.defaultCorrelationId;
            BigDecimal amount = TransactionBuilder.defaultAmount;

            TransactionResponseDto expectedResponseDto = TransactionBuilder.Response.validOfType(TransactionType.DEBIT).build();

            when(transactionMapper.toResponseDto(any(Transaction.class))).thenReturn(expectedResponseDto);

            TransactionResponseDto result = transactionService.debit(accountId, correlationId, amount);

            assertNotNull(result);
            assertEquals(expectedResponseDto, result);

            verify(transactionRepository).save(transactionCaptor.capture());
            Transaction savedTransaction = transactionCaptor.getValue();

            assertNotNull(savedTransaction.getId());
            assertEquals(accountId, savedTransaction.getAccountId());
            assertEquals(correlationId, savedTransaction.getCorrelationId());
            assertEquals(amount, savedTransaction.getAmount());
            assertEquals(TransactionType.DEBIT, savedTransaction.getType());

            verify(transactionMapper).toResponseDto(savedTransaction);
        }

    }

    @Nested
    @DisplayName(value = "Testes de crédito")
    class CreditTests {

        @Test
        void shouldCreateAndSaveCreditTransactionSuccessfully() {
            UUID accountId = TransactionBuilder.defaultAccountId;
            UUID correlationId = TransactionBuilder.defaultCorrelationId;
            BigDecimal amount = TransactionBuilder.defaultAmount;

            TransactionResponseDto expectedResponseDto = TransactionBuilder.Response.validOfType(TransactionType.CREDIT).build();

            when(transactionMapper.toResponseDto(any(Transaction.class))).thenReturn(expectedResponseDto);

            TransactionResponseDto result = transactionService.credit(accountId, correlationId, amount);

            assertNotNull(result);
            assertEquals(expectedResponseDto, result);

            verify(transactionRepository).save(transactionCaptor.capture());
            Transaction savedTransaction = transactionCaptor.getValue();

            assertNotNull(savedTransaction.getId());
            assertEquals(accountId, savedTransaction.getAccountId());
            assertEquals(correlationId, savedTransaction.getCorrelationId());
            assertEquals(amount, savedTransaction.getAmount());
            assertEquals(TransactionType.CREDIT, savedTransaction.getType());

            verify(transactionMapper).toResponseDto(savedTransaction);
        }

    }

}
