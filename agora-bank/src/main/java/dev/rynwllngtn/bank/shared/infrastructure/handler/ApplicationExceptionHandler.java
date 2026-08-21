package dev.rynwllngtn.bank.shared.infrastructure.handler;

import dev.rynwllngtn.bank.account.domain.exception.InactiveAccountException;
import dev.rynwllngtn.bank.account.domain.exception.InsufficientFundsException;
import dev.rynwllngtn.bank.account.domain.exception.InvalidAmountException;
import dev.rynwllngtn.bank.shared.application.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ProblemDetail resourceNotFound(ResourceNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                                                                       e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(value = InvalidAmountException.class)
    public ProblemDetail invalidAmount(InvalidAmountException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    public ProblemDetail insufficientFunds(InsufficientFundsException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,
                                                                       e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(value = InactiveAccountException.class)
    public ProblemDetail inactiveAccount(InactiveAccountException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT,
                                                                       e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

}