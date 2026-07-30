package dev.rynwllngtn.bank.shared.infrastructure.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValid(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                                                                 "Erro em validação em " + e.getFieldErrorCount() + " campos!");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("fields", getErrors(e.getBindingResult().getFieldErrors()));
        return problemDetail;
    }

    private Map<String, List<String>> getErrors(List<FieldError> fields) {
        return fields.stream()
                .filter(field -> field.getDefaultMessage() != null)
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
    }

}