package dev.rynwllngtn.bank.customer.application.service;

import dev.rynwllngtn.bank.customer.application.dto.CustomerCreateRequestDto;
import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.customer.application.mapper.CustomerMapper;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private Customer findByIdOrThrow(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElseThrow(
                () -> new ResourceNotFoundException("Customer não encontrado!")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto findById(UUID id) {
        Customer customer = findByIdOrThrow(id);
        return customerMapper.toResponseDto(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDto create(CustomerCreateRequestDto createRequestDto) {
        Customer customer = customerMapper.toEntity(createRequestDto);
        customer = customerRepository.save(customer);
        return customerMapper.toResponseDto(customer);
    }

}