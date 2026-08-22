package dev.rynwllngtn.bank.operation.deposit.builder;

import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositRequestDto;
import dev.rynwllngtn.bank.operation.deposit.application.dto.DepositResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DepositBuilder {

    public static UUID defaultAccountId = UUID.randomUUID();
    public static UUID defaultTransactionId = UUID.randomUUID();
    public static UUID defaultCorrelationId = UUID.randomUUID();

    public static class Request {

        private UUID accountId = defaultAccountId;
        private BigDecimal amount = new BigDecimal("100.00");

        private Request() {
        }

        public static Request valid() {
            return new Request();
        }

        public Request withAccountId(UUID accountId) {
            this.accountId = accountId;
            return this;
        }

        public Request withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public DepositRequestDto build() {
            return new DepositRequestDto(accountId, amount);
        }

    }

    public static class Response {

        private UUID accountId = defaultAccountId;
        private UUID transactionId = defaultTransactionId;
        private UUID correlationId = defaultCorrelationId;
        private BigDecimal amount = new BigDecimal("100.00");
        private BigDecimal newBalance = BigDecimal.ZERO;
        private LocalDateTime timestamp = LocalDateTime.now();

        private Response() {
        }

        public static Response valid() {
            return new Response();
        }

        public Response withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public DepositResponseDto build() {
            return new DepositResponseDto(accountId, transactionId, correlationId, newBalance, amount, timestamp);
        }

    }

}