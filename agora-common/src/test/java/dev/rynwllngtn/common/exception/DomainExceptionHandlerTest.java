package dev.rynwllngtn.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DomainExceptionHandlerTest {

    static class TestException extends DomainException {
        public TestException(String message) {
            super(message);
        }
    }

    @Test
    void shouldReturnFormattedProblemDetail() {
        DomainExceptionHandler handler = new DomainExceptionHandler();

        String error = "Something wrong happened!";
        ProblemDetail detail = handler.domainException(new TestException(error));

        assertNotNull(detail);
        assertNotNull(detail.getProperties().get("timestamp"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), detail.getStatus());
        assertEquals(error, detail.getDetail());
    }

}