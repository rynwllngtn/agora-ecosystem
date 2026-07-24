package dev.rynwllngtn.identity.application.producer;

import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;

public interface IdentityEventProducer {

    void identityCreated(IdentityCreatedEvent createdEvent);

    void emailUpdated(IdentityEmailUpdatedEvent updatedEvent);

}