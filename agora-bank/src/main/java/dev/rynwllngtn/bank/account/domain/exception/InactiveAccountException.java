package dev.rynwllngtn.bank.account.domain.exception;

import dev.rynwllngtn.common.exception.DomainException;

public class InactiveAccountException extends DomainException {
    public InactiveAccountException(String message) {
        super(message);
    }
}