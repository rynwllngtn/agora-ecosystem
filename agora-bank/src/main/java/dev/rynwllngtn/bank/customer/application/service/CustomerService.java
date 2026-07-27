package dev.rynwllngtn.bank.customer.application.service;

import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;

import java.util.UUID;

public interface CustomerService {

    CustomerResponseDto findById(UUID id);

    void create(IdentityCreatedEvent createdEvent);
    void updateEmail(IdentityEmailUpdatedEvent emailUpdatedEvent);

}