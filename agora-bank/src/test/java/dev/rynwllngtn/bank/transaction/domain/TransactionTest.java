package dev.rynwllngtn.bank.transaction.domain;

import dev.rynwllngtn.bank.transaction.builder.TransactionBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionTest {

    @Nested
    @DisplayName(value = "Testes de inicialização")
    class InitializationTest {
        @Test
        void shouldInitializeValidDebitTransaction() {
            UUID id = UUID.randomUUID();
            Transaction mockTransaction = TransactionBuilder.Entity.validOfType(TransactionType.DEBIT).build();
            Transaction mockTransaction2 = TransactionBuilder.Entity.validOfType(TransactionType.DEBIT).build();

            TransactionBuilder.changeId(mockTransaction, id);
            TransactionBuilder.changeId(mockTransaction2, id);

            assertNotNull(mockTransaction);
            assertNotNull(mockTransaction.getId());
            assertEquals(mockTransaction2, mockTransaction);
            assertEquals(TransactionBuilder.defaultAmount, mockTransaction.getAmount());
            assertEquals(TransactionType.DEBIT, mockTransaction.getType());
        }
        @Test
        void shouldInitializeValidCreditTransaction() {
            UUID id = UUID.randomUUID();
            Transaction mockTransaction = TransactionBuilder.Entity.validOfType(TransactionType.CREDIT).build();
            Transaction mockTransaction2 = TransactionBuilder.Entity.validOfType(TransactionType.CREDIT).build();

            TransactionBuilder.changeId(mockTransaction, id);
            TransactionBuilder.changeId(mockTransaction2, id);

            assertNotNull(mockTransaction);
            assertNotNull(mockTransaction.getId());
            assertEquals(mockTransaction2, mockTransaction);
            assertEquals(TransactionBuilder.defaultAmount, mockTransaction.getAmount());
            assertEquals(TransactionType.CREDIT, mockTransaction.getType());
        }
    }

}