package dev.rynwllngtn.bank.account.builder;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.account.domain.Account;
import dev.rynwllngtn.bank.account.domain.AccountDetails;
import dev.rynwllngtn.bank.account.domain.AccountStatus;
import org.springframework.test.util.ReflectionTestUtils;

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
        private BigDecimal balance = BigDecimal.ZERO;

        private Entity() {
        }

        public static Entity valid() {
            return new Entity();
        }

        public Entity withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Account build() {
            Account account = new Account(customerId, details);
            if (this.balance != null && this.balance.compareTo(BigDecimal.ZERO) != 0) {
                ReflectionTestUtils.setField(account, "balance", this.balance);
            }
            return account;
        }

    }

    public static class Response {

        private UUID id = UUID.randomUUID();
        private UUID customerId = defaultCustomerId;
        private String agency = "001";
        private String number = defaultAccountNumber;
        private String bankCode = "1234";
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
            return new AccountResponseDto(id, customerId, agency, number, bankCode, status, balance);
        }

        public static AccountResponseDto fromEntity(Account account) {
            return new AccountResponseDto(
                    account.getId(),
                    account.getCustomerId(),
                    account.getDetails().agency(),
                    account.getDetails().number(),
                    account.getDetails().bankCode(),
                    account.getStatus(),
                    account.getBalance()
            );
        }

    }

}
