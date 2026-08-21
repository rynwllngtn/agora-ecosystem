package dev.rynwllngtn.bank.account.domain.exception;

import dev.rynwllngtn.common.exception.DomainException;

public class InvalidAmountException extends DomainException {
    public InvalidAmountException(String message) {
        super(message);
    }
}