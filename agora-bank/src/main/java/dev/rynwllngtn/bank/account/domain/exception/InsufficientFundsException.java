package dev.rynwllngtn.bank.account.domain.exception;

import dev.rynwllngtn.common.exception.DomainException;

public class InsufficientFundsException extends DomainException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}