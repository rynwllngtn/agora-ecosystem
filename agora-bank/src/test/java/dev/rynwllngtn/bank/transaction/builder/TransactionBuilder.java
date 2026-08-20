package dev.rynwllngtn.bank.transaction.builder;

import dev.rynwllngtn.bank.transaction.application.dto.TransactionResponseDto;
import dev.rynwllngtn.bank.transaction.domain.Transaction;
import dev.rynwllngtn.bank.transaction.domain.TransactionType;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionBuilder {

    public static UUID defaultAccountId = UUID.randomUUID();
    public static UUID defaultCorrelationId = UUID.randomUUID();
    public static BigDecimal defaultAmount = BigDecimal.ZERO;

    public static class Entity {

        private UUID accountId = defaultAccountId;
        private UUID correlationId = defaultCorrelationId;
        private BigDecimal amount = defaultAmount;
        private TransactionType type;

        private Entity() {
        }

        private Entity(TransactionType type) {
            this.type = type;
        }

        public static Entity validOfType(TransactionType type) {
            return new Entity(type);
        }

        public Transaction build() {
            if (this.type == TransactionType.CREDIT) {
                return Transaction.getCreditInstance(accountId, correlationId, amount);
            }
            return Transaction.getDebitInstance(accountId, correlationId, amount);
        }

    }

    public static class Response {

        private UUID id = UUID.randomUUID();
        private TransactionType type;

        private Response() {
        }

        private Response(TransactionType type) {
            this.type = type;
        }

        public static Response valid() {
            return new Response();
        }

        public static Response validOfType(TransactionType type) {
            return new Response(type);
        }

        public Response withId(UUID id) {
            this.id = id;
            return this;
        }

        public TransactionResponseDto build() {
            return new TransactionResponseDto(id, defaultAccountId, defaultCorrelationId, defaultAmount, type);
        }

        public static TransactionResponseDto fromEntity(Transaction transaction) {
            return new TransactionResponseDto(transaction.getId(),
                                              transaction.getAccountId(),
                                              transaction.getCorrelationId(),
                                              transaction.getAmount(),
                                              transaction.getType());
        }

    }

    public static void changeId(Transaction transaction, UUID id) {
        ReflectionTestUtils.setField(
                transaction,
                "id",
                id
        );
    }

}