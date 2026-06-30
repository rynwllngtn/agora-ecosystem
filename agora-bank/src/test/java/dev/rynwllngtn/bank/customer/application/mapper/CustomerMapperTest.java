package dev.rynwllngtn.bank.customer.application.mapper;

import dev.rynwllngtn.bank.customer.application.dto.CustomerCreateRequestDto;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.builder.CustomerBuilder;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerMapperTest {

    private final CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void shouldMapCustomerToCustomerResponseDto() {
        Customer customer = CustomerBuilder.Entity.valid().build();

        CustomerResponseDto responseDto = customerMapper.toResponseDto(customer);

        assertNotNull(responseDto);
        assertEquals(customer.getId(), responseDto.id());
        assertEquals(customer.getEmail(), responseDto.email());
        assertEquals(CustomerStatus.PENDING_REGISTRATION, responseDto.status());
    }

    @Test
    void shouldMapCustomerCreateRequestDtoToCustomer() {
        CustomerCreateRequestDto createRequestDto = CustomerBuilder.CreateRequest.valid().build();

        Customer customer = customerMapper.toEntity(createRequestDto);

        assertNotNull(customer);
        assertEquals(createRequestDto.identityId(), customer.getIdentityId());
        assertEquals(createRequestDto.cpf(), customer.getCpf());
        assertEquals(createRequestDto.email(), customer.getEmail());
    }

}
