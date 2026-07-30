package dev.rynwllngtn.bank.account.domain;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<Account> findById(UUID id);

    boolean existsByCustomerId(UUID customerId);

    Long nextAccountNumberFromSequence();

    Account save(Account account);

}