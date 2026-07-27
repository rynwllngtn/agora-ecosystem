package dev.rynwllngtn.bank.customer.api.messaging;

import dev.rynwllngtn.bank.customer.application.service.CustomerService;
import dev.rynwllngtn.bank.customer.builder.CustomerBuilder;
import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IdentityEventConsumerTest {

    @Mock
    private CustomerService customerService;

    private IdentityEventConsumer consumer;

    @BeforeEach
    void setUp() {
        consumer = new IdentityEventConsumer(customerService);
    }

    @Nested
    @DisplayName(value = "Testes de consumo de IdentityCreatedEvent")
    class CreateCustomerTests {

        @Test
        void shouldDelegateToCustomerServiceCreateWhenEventIsReceived() {
            IdentityCreatedEvent createdEvent = CustomerBuilder.CreateEvent.valid().build();

            consumer.createCustomer(createdEvent);

            verify(customerService).create(createdEvent);
        }

        @Test
        void shouldPassSameEventReceivedToCustomerService() {
            IdentityCreatedEvent createdEvent = CustomerBuilder.CreateEvent.valid().build();

            consumer.createCustomer(createdEvent);

            ArgumentCaptor<IdentityCreatedEvent> captor = ArgumentCaptor.forClass(IdentityCreatedEvent.class);
            verify(customerService).create(captor.capture());

            assertEquals(createdEvent.id(), captor.getValue().id());
            assertEquals(createdEvent.cpf(), captor.getValue().cpf());
            assertEquals(createdEvent.email(), captor.getValue().email());
        }

    }

    @Nested
    @DisplayName(value = "Testes de consumo de IdentityEmailUpdatedEvent")
    class UpdateEmailTests {

        @Test
        void shouldDelegateToCustomerServiceUpdateEmailWhenEventIsReceived() {
            IdentityEmailUpdatedEvent event = CustomerBuilder.UpdatedEmailEvent.valid().build();

            consumer.updateEmail(event);

            verify(customerService).updateEmail(event);
        }

        @Test
        void shouldPassSameEventReceivedToCustomerService() {
            IdentityEmailUpdatedEvent event = CustomerBuilder.UpdatedEmailEvent.valid().build();

            consumer.updateEmail(event);

            ArgumentCaptor<IdentityEmailUpdatedEvent> captor = ArgumentCaptor.forClass(IdentityEmailUpdatedEvent.class);
            verify(customerService).updateEmail(captor.capture());

            assertEquals(event.id(), captor.getValue().id());
            assertEquals(event.email(), captor.getValue().email());
        }

    }

}