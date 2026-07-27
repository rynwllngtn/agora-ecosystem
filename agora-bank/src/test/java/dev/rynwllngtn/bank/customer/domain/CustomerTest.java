package dev.rynwllngtn.bank.customer.domain;

import dev.rynwllngtn.bank.customer.builder.CustomerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        mockCustomer = CustomerBuilder.Entity.valid().build();
    }

    @Nested
    @DisplayName(value = "Testes de inicialização")
    class InitializationTest {
        @Test
        void shouldInitializeValidCustomer() {
            assertNotNull(mockCustomer);
            assertNotNull(mockCustomer.getId());

            UUID id = UUID.randomUUID();
            Customer mockCustomer2 = CustomerBuilder.Entity.valid().build();

            ReflectionTestUtils.setField(
                    mockCustomer,
                    "id",
                    id
            );
            ReflectionTestUtils.setField(
                    mockCustomer2,
                    "id",
                    id
            );

            assertEquals(mockCustomer, mockCustomer2);
            assertEquals(CustomerBuilder.defaultCpf, mockCustomer.getCpf());
            assertEquals(CustomerStatus.PENDING_REGISTRATION, mockCustomer.getStatus());
        }
    }

    @Nested
    @DisplayName(value = "Testes de métodos")
    class MethodsTest {
        @Test
        void shouldUpdateEmailField() {
            assertNotNull(mockCustomer);
            assertNotNull(mockCustomer.getId());

            String newEmail = "";
            mockCustomer.updateEmail(newEmail);

            assertEquals(newEmail, mockCustomer.getEmail());
        }
    }

}
