package dev.rynwllngtn.bank.customer.application.mapper;

import dev.rynwllngtn.bank.customer.application.dto.CustomerCreateRequestDto;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDto toResponseDto(Customer customer) {
        return new CustomerResponseDto(customer.getId(),
                                       customer.getCpf(),
                                       customer.getEmail());
    }

    public Customer toEntity(CustomerCreateRequestDto createRequestDto) {
        return new Customer(createRequestDto.identityId(),
                            createRequestDto.cpf(),
                            createRequestDto.email());
    }

}