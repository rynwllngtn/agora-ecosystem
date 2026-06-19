package dev.rynwllngtn.identity.application.exception;

import dev.rynwllngtn.common.exception.DomainException;

public class WrongPasswordException extends DomainException {
    public WrongPasswordException(String message) {
        super(message);
    }
}