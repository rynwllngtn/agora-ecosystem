package dev.rynwllngtn.identity.application.exception;

import dev.rynwllngtn.common.exception.DomainException;

public class DuplicateResourceException extends DomainException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}