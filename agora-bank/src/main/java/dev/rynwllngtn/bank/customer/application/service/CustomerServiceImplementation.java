package dev.rynwllngtn.bank.customer.application.service;

import dev.rynwllngtn.bank.customer.application.dto.CustomerResponseDto;
import dev.rynwllngtn.bank.customer.application.exception.ResourceNotFoundException;
import dev.rynwllngtn.bank.customer.application.mapper.CustomerMapper;
import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerRepository;
import dev.rynwllngtn.common.event.identity.IdentityCreatedEvent;
import dev.rynwllngtn.common.event.identity.IdentityEmailUpdatedEvent;
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

    private Customer findByIdentityIdOrThrow(UUID identityId) {
        Optional<Customer> customer = customerRepository.findByIdentityId(identityId);
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
    public void create(IdentityCreatedEvent createdEvent) {
        if (customerRepository.existsByIdentityId(createdEvent.id())) {
            return;
        }
        Customer customer = customerMapper.toEntity(createdEvent);
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void updateEmail(IdentityEmailUpdatedEvent emailUpdatedEvent) {
        Customer customer = findByIdentityIdOrThrow(emailUpdatedEvent.id());
        if (customer.getUpdatedAt().isAfter(emailUpdatedEvent.updatedAt())) {
            return;
        }
        customer.updateEmail(emailUpdatedEvent.email());
        customerRepository.save(customer);
    }

}