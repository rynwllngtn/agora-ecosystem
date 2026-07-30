package dev.rynwllngtn.bank.account.builder;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.domain.Account;
import dev.rynwllngtn.bank.account.domain.AccountDetails;
import dev.rynwllngtn.bank.account.domain.AccountStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountBuilder {

    public static UUID defaultCustomerId = UUID.randomUUID();
    public static String defaultAccountNumber = "12345678";

    private AccountBuilder() {
    }

    public static class Entity {

        private UUID customerId = defaultCustomerId;
        private final AccountDetails details = new AccountDetails(defaultAccountNumber);

        private Entity() {
        }

        public static Entity valid() {
            return new Entity();
        }

        public Account build() {
            return new Account(customerId, details);
        }

    }

    public static class Response {

        private UUID id = UUID.randomUUID();
        private UUID customerId = defaultCustomerId;
        private AccountDetails details = new AccountDetails(defaultAccountNumber);
        private AccountStatus status = AccountStatus.ACTIVE;
        private BigDecimal balance = BigDecimal.ZERO;

        private Response() {}

        public static Response valid() {
            return new Response();
        }

        public Response withId(UUID id) {
            this.id = id;
            return this;
        }

        public AccountResponseDto build() {
            return new AccountResponseDto(id, customerId, details, status, balance);
        }

        public static AccountResponseDto fromEntity(Account account) {
            return new AccountResponseDto(
                    account.getId(),
                    account.getCustomerId(),
                    account.getDetails(),
                    account.getStatus(),
                    account.getBalance()
            );
        }

    }

}