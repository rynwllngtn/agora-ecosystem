package dev.rynwllngtn.bank.account.api.http;

import dev.rynwllngtn.bank.account.application.dto.AccountResponseDto;
import dev.rynwllngtn.bank.shared.application.event.CustomerRegisteredEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@Tag(
        name = "Endpoint Teste"
)
@RestController
@RequestMapping(value = "/account/test")
public class AccountControllerTest {

    private final ApplicationEventPublisher eventPublisher;

    @Operation(
            summary = "Rota teste de lançamento de evento para criação",
            operationId = "customerRegistered"
    )
    @PostMapping(value = "/{customerId}")
    public ResponseEntity<AccountResponseDto> customerRegistered(@PathVariable(value = "customerId") UUID customerId) {
        CustomerRegisteredEvent registeredEvent = new CustomerRegisteredEvent(customerId);
        eventPublisher.publishEvent(registeredEvent);
        return ResponseEntity.ok().build();
    }

}