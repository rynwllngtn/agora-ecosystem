package dev.rynwllngtn.bank.customer.infrastructure.persistance;

import dev.rynwllngtn.bank.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryJpa extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByIdentityId(UUID identityId);
    boolean existsByIdentityId(UUID identityId);
}