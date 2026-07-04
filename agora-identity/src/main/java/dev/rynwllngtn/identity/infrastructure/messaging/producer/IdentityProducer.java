package dev.rynwllngtn.identity.infrastructure.messaging.producer;

import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import dev.rynwllngtn.identity.application.producer.IdentityEventProducer;
import dev.rynwllngtn.identity.infrastructure.config.RabbitMQConfig;
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
                RabbitMQConfig.EXCHANGE_NAME,
                "identity.created",
                createdEvent
        );
    }

    @Override
    public void emailUpdated(IdentityEmailUpdatedEvent updatedEvent) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                "identity.email.updated",
                updatedEvent
        );
    }

}