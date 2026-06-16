package dev.rynwllngtn.identity.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IdentityTest {

    private Identity identity;

    @BeforeEach
    void setUp() {
        identity = new Identity("11122233344", "password", "email@email.com");
    }

    @Test
    void shouldInitializeValidIdentity() {
        assertNotNull(identity);
        assertNotNull(identity.getId());

        UUID id = UUID.randomUUID();
        Identity identity2 = new Identity("11122233344", "password", "email@email.com");

        ReflectionTestUtils.setField(
                identity,
                "id",
                id
        );
        ReflectionTestUtils.setField(
                identity2,
                "id",
                id
        );

        assertEquals(identity, identity2);
        assertEquals("11122233344", identity.getCpf());
        assertEquals(IdentityStatus.ACTIVE, identity.getStatus());
    }

    @Test
    void shouldUpdateTheEmail() {
        identity.changeEmail("newEmail@email.com");
        assertEquals("newEmail@email.com", identity.getEmail());
    }

    @Test
    void shouldUpdateThePassword() {
        identity.changePassword("newPassword");
        assertEquals("newPassword", identity.getPassword());
    }

    @Test
    void shouldChangeStatusToDeactivated() {
        identity.deactivate();
        assertEquals(IdentityStatus.DEACTIVATED, identity.getStatus());
    }

    @Test
    void shouldChangeStatusToSuspended() {
        identity.suspend();
        assertEquals(IdentityStatus.SUSPENDED, identity.getStatus());
    }

    @Test
    void shouldChangeStatusToActive_whenSuspended() {
        identity.suspend();
        identity.activate();
        assertEquals(IdentityStatus.ACTIVE, identity.getStatus());
    }

}