package dev.rynwllngtn.bank.operation.withdraw.builder;

import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawRequestDto;
import dev.rynwllngtn.bank.operation.withdraw.application.dto.WithdrawResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class WithdrawBuilder {

    public static UUID defaultAccountId = UUID.randomUUID();
    public static UUID defaultTransactionId = UUID.randomUUID();
    public static UUID defaultCorrelationId = UUID.randomUUID();

    public static class Request {

        private UUID accountId = defaultAccountId;
        private BigDecimal amount = new BigDecimal("50.00");

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

        public WithdrawRequestDto build() {
            return new WithdrawRequestDto(accountId, amount);
        }

    }

    public static class Response {

        private UUID accountId = defaultAccountId;
        private UUID transactionId = defaultTransactionId;
        private UUID correlationId = defaultCorrelationId;
        private BigDecimal amount = new BigDecimal("50.00");
        private BigDecimal newBalance = new BigDecimal("50.00");
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

        public WithdrawResponseDto build() {
            return new WithdrawResponseDto(accountId, transactionId, correlationId, amount, newBalance, timestamp);
        }

    }

}