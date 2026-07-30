package dev.rynwllngtn.bank.shared.application.exception;

import dev.rynwllngtn.common.exception.DomainException;

public class ResourceNotFoundException extends DomainException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}