package dev.rynwllngtn.identity.infrastructure.handler;

import dev.rynwllngtn.identity.application.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ProblemDetail resourceNotFound(ResourceNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                                                                       e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

}