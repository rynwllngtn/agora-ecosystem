package dev.rynwllngtn.bank.customer.api.messaging;

import dev.rynwllngtn.bank.customer.application.service.CustomerService;
import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
import dev.rynwllngtn.common.messaging.IdentityMessagingConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IdentityEventConsumer {

    private final CustomerService customerService;

    private static final String QUEUE_CREATED = "bank.customer.created.queue";
    private static final String QUEUE_EMAIL_UPDATED = "bank.customer.email.updated.queue";

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_CREATED),
            exchange = @Exchange(value = IdentityMessagingConstants.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = IdentityMessagingConstants.CREATED
    ))
    public void createCustomer(IdentityCreatedEvent createdEvent) {
        customerService.create(createdEvent);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_EMAIL_UPDATED),
            exchange = @Exchange(value = IdentityMessagingConstants.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = IdentityMessagingConstants.UPDATED_EMAIL
    ))
    public void updateEmail(IdentityEmailUpdatedEvent emailUpdatedEvent) {
        customerService.updateEmail(emailUpdatedEvent);
    }

}