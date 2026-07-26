package dev.rynwllngtn.bank.customer.infrastructure.persistance;

import dev.rynwllngtn.bank.customer.domain.Customer;
import dev.rynwllngtn.bank.customer.domain.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerRepositoryJpa customerRepository;

    @Override
    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByIdentityId(UUID identityId) {
        return customerRepository.findByIdentityId(identityId);
    }

    @Override
    public boolean existsByIdentityId(UUID identityId) {
        return customerRepository.existsByIdentityId(identityId);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

}