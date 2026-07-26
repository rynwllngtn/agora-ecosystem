package dev.rynwllngtn.bank.customer.application.mapper;

import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDto toResponseDto(Customer customer) {
        return new CustomerResponseDto(customer.getId(),
                                       customer.getEmail(),
                                       customer.getStatus());
    }

    public Customer toEntity(IdentityCreatedEvent createdEvent) {
        return new Customer(createdEvent.id(),
                            createdEvent.cpf(),
                            createdEvent.email());
    }

}