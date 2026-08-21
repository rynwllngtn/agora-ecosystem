package dev.rynwllngtn.bank.account.domain;

import dev.rynwllngtn.bank.account.builder.AccountBuilder;
import dev.rynwllngtn.bank.account.domain.exception.InactiveAccountException;
import dev.rynwllngtn.bank.account.domain.exception.InsufficientFundsException;
import dev.rynwllngtn.bank.account.domain.exception.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Testes de ciclo de vida")
    class LifecycleTest {
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

    @Nested
    @DisplayName("Testes de depósito")
    class DepositTest {
        @Test
        void shouldDepositAmountSuccessfully() {
            BigDecimal depositAmount = new BigDecimal("150.00");
            mockAccount.deposit(depositAmount);

            assertEquals(depositAmount, mockAccount.getBalance());
        }

        @Test
        void shouldThrowInactiveAccountExceptionWhenAccountIsNotActive() {
            mockAccount.deactivate();

            assertThrows(InactiveAccountException.class, () ->
                    mockAccount.deposit(new BigDecimal("100.00"))
            );
        }

        @Test
        void shouldThrowInvalidAmountExceptionWhenAmountIsNullOrZeroOrNegative() {
            assertThrows(InvalidAmountException.class, () ->
                    mockAccount.deposit(null)
            );
            assertThrows(InvalidAmountException.class, () ->
                    mockAccount.deposit(BigDecimal.ZERO)
            );
            assertThrows(InvalidAmountException.class, () ->
                    mockAccount.deposit(new BigDecimal("-50.00"))
            );
        }
    }

    @Nested
    @DisplayName("Testes de saque")
    class WithdrawTest {
        @Test
        void shouldWithdrawAmountSuccessfully() {
            mockAccount.deposit(new BigDecimal("200.00"));
            mockAccount.withdraw(new BigDecimal("50.00"));

            assertEquals(new BigDecimal("150.00"), mockAccount.getBalance());
        }

        @Test
        void shouldThrowInactiveAccountExceptionWhenAccountIsNotActive() {
            mockAccount.deposit(new BigDecimal("200.00"));
            mockAccount.suspend();

            assertThrows(InactiveAccountException.class, () ->
                    mockAccount.withdraw(new BigDecimal("50.00"))
            );
        }

        @Test
        void shouldThrowInvalidAmountExceptionWhenAmountIsNullOrZeroOrNegative() {
            mockAccount.deposit(new BigDecimal("200.00"));

            assertThrows(InvalidAmountException.class, () ->
                    mockAccount.withdraw(null)
            );
            assertThrows(InvalidAmountException.class, () ->
                    mockAccount.withdraw(BigDecimal.ZERO)
            );
            assertThrows(InvalidAmountException.class, () ->
                    mockAccount.withdraw(new BigDecimal("-50.00"))
            );
        }

        @Test
        void shouldThrowInsufficientFundsExceptionWhenBalanceIsLessThanAmount() {
            mockAccount.deposit(new BigDecimal("100.00"));

            assertThrows(InsufficientFundsException.class, () ->
                    mockAccount.withdraw(new BigDecimal("150.00"))
            );
        }
    }

}
