package dev.rynwllngtn.identity.domain;

import dev.rynwllngtn.identity.builder.IdentityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IdentityTest {

    private Identity mockIdentity;

    @BeforeEach
    void setUp() {
        mockIdentity = IdentityBuilder.Entity.valid().build();
    }

    @Nested
    @DisplayName(value = "Testes de inicialização")
    class InitializationTest {

        @Test
        void shouldInitializeValidIdentity() {
            assertNotNull(mockIdentity);
            assertNotNull(mockIdentity.getId());

            UUID id = UUID.randomUUID();
            Identity mockIdentity2 = IdentityBuilder.Entity.valid().build();

            ReflectionTestUtils.setField(
                    mockIdentity,
                    "id",
                    id
            );
            ReflectionTestUtils.setField(
                    mockIdentity2,
                    "id",
                    id
            );

            assertEquals(mockIdentity, mockIdentity2);
            assertEquals(IdentityBuilder.defaultCpf, mockIdentity.getCpf());
            assertEquals(IdentityStatus.ACTIVE, mockIdentity.getStatus());
        }

    }

    @Nested
    @DisplayName(value = "Testes de atualização de dados")
    class UpdatingDataTest {

        @Test
        void shouldUpdateTheEmail() {
            mockIdentity.changeEmail(IdentityBuilder.updateEmail);
            assertEquals(IdentityBuilder.updateEmail, mockIdentity.getEmail());
        }

        @Test
        void shouldUpdateThePassword() {
            mockIdentity.changePassword(IdentityBuilder.updatePassword);
            assertEquals(IdentityBuilder.updatePassword, mockIdentity.getPassword());
        }

    }

    @Nested
    @DisplayName(value = "Testes de mudança de status")
    class UpdatingStatusTest {

        @Test
        void shouldChangeStatusToDeactivated() {
            mockIdentity.deactivate();
            assertEquals(IdentityStatus.DEACTIVATED, mockIdentity.getStatus());
        }

        @Test
        void shouldChangeStatusToSuspended() {
            mockIdentity.suspend();
            assertEquals(IdentityStatus.SUSPENDED, mockIdentity.getStatus());
        }

        @Test
        void shouldChangeStatusToActiveWhenSuspended() {
            mockIdentity.suspend();
            mockIdentity.activate();
            assertEquals(IdentityStatus.ACTIVE, mockIdentity.getStatus());
        }

    }

}