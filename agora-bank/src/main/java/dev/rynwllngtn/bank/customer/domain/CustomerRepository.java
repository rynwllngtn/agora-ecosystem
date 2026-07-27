package dev.rynwllngtn.bank.customer.domain;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findById(UUID id);
    Optional<Customer> findByIdentityId(UUID identityId);

    boolean existsByIdentityId(UUID identityId);

    Customer save(Customer customer);

}