package dev.rynwllngtn.bank.account.domain;

import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountTest {

    private Account mockAccount;

    @BeforeEach
    void setUp() {
        mockAccount = AccountBuilder.Entity.valid().build();
    }

    @Nested
    @DisplayName("Testes de inicialização")
    class InitializationTest {
        @Test
        void shouldInitializeValidAccount() {
            assertNotNull(mockAccount);
            assertNotNull(mockAccount.getId());

            UUID id = UUID.randomUUID();
            Account mockAccount2 = AccountBuilder.Entity.valid().build();

            ReflectionTestUtils.setField(
                    mockAccount,
                    "id",
                    id
            );
            ReflectionTestUtils.setField(
                    mockAccount2,
                    "id",
                    id
            );

            assertEquals(mockAccount, mockAccount2);
            assertEquals(AccountBuilder.defaultCustomerId, mockAccount.getCustomerId());
            assertEquals(AccountStatus.ACTIVE, mockAccount.getStatus());
            assertEquals(BigDecimal.ZERO, mockAccount.getBalance());
        }
    }

    @Nested
    @DisplayName("Testes de métodos")
    class MethodsTest {
        @Test
        void shouldActivateAccount() {
            mockAccount.deactivate();
            mockAccount.activate();
            assertEquals(AccountStatus.ACTIVE, mockAccount.getStatus());
        }
        @Test
        void shouldDeactivateAccount() {
            mockAccount.deactivate();
            assertEquals(AccountStatus.INACTIVE, mockAccount.getStatus());
        }
        @Test
        void shouldSuspendAccount() {
            mockAccount.suspend();
            assertEquals(AccountStatus.SUSPENDED, mockAccount.getStatus());
        }
    }

}