package dev.rynwllngtn.bank.account.api.listener;

import dev.rynwllngtn.bank.account.application.service.AccountService;
import dev.rynwllngtn.bank.shared.application.event.CustomerRegisteredEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerListenerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private CustomerEventListener customerEventListener;

    @Test
    @DisplayName("Teste para criação após registro de Customer")
    void shouldCreateAccountWhenCustomerIsRegistered() {
        UUID customerId = UUID.randomUUID();
        CustomerRegisteredEvent event = new CustomerRegisteredEvent(customerId);

        customerEventListener.customerRegistered(event);

        verify(accountService).create(customerId);
    }

}