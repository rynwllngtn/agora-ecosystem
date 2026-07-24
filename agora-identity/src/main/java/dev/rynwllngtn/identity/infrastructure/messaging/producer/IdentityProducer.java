package dev.rynwllngtn.identity.infrastructure.messaging.producer;

import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import dev.rynwllngtn.common.messaging.IdentityMessagingConstants;
import dev.rynwllngtn.identity.application.producer.IdentityEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IdentityProducer implements IdentityEventProducer {

    private final AmqpTemplate amqpTemplate;

    @Override
    public void identityCreated(IdentityCreatedEvent createdEvent) {
        amqpTemplate.convertAndSend(
                IdentityMessagingConstants.EXCHANGE_NAME,
                IdentityMessagingConstants.CREATED,
                createdEvent
        );
    }

    @Override
    public void emailUpdated(IdentityEmailUpdatedEvent updatedEvent) {
        amqpTemplate.convertAndSend(
                IdentityMessagingConstants.EXCHANGE_NAME,
                IdentityMessagingConstants.UPDATED_EMAIL,
                updatedEvent
        );
    }

}