package dev.rynwllngtn.bank.account.api.http;

import dev.rynwllngtn.bank.shared.application.event.CustomerRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/test/account")
public class AccountControllerTest {

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/{customerId}")
    public ResponseEntity<Void> testEventDispatch(@PathVariable(value = "customerId") UUID customerId) {
        CustomerRegisteredEvent event = new CustomerRegisteredEvent(customerId);
        eventPublisher.publishEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}