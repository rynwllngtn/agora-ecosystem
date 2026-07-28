package dev.rynwllngtn.identity.infrastructure.messaging.producer;

import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import dev.rynwllngtn.common.messaging.IdentityMessagingConstants;
import dev.rynwllngtn.identity.builder.IdentityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IdentityProducerTest {

    @Mock
    private AmqpTemplate amqpTemplate;

    @InjectMocks
    private IdentityProducer identityProducer;

    @Nested
    @DisplayName(value = "Testes de publicação de IdentityCreatedEvent")
    class IdentityCreatedTests {

        @Test
        void shouldSendExactEventReceivedAsPayload() {
            IdentityCreatedEvent createdEvent = IdentityBuilder.CreateEvent.valid().build();


            identityProducer.identityCreated(createdEvent);

            ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);
            verify(amqpTemplate).convertAndSend(
                    eq(IdentityMessagingConstants.EXCHANGE_NAME),
                    eq(IdentityMessagingConstants.CREATED),
                    payloadCaptor.capture()
            );

            assertEquals(createdEvent, payloadCaptor.getValue());
        }

    }

    @Nested
    @DisplayName(value = "Testes de publicação de IdentityEmailUpdatedEvent")
    class EmailUpdatedTests {

        @Test
        void shouldPublishToCorrectExchangeAndRoutingKey() {
            IdentityEmailUpdatedEvent emailUpdatedEvent = IdentityBuilder.UpdatedEmailEvent.valid().build();

            identityProducer.emailUpdated(emailUpdatedEvent);

            verify(amqpTemplate).convertAndSend(
                    IdentityMessagingConstants.EXCHANGE_NAME,
                    IdentityMessagingConstants.UPDATED_EMAIL,
                    emailUpdatedEvent
            );
        }

    }

}