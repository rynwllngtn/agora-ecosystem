package dev.rynwllngtn.bank.customer.domain;

import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById();

    Customer save();

}