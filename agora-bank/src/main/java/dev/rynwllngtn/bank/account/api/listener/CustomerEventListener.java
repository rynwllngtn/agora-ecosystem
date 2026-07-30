package dev.rynwllngtn.bank.account.api.listener;

import dev.rynwllngtn.bank.account.application.service.AccountService;
import dev.rynwllngtn.bank.shared.application.event.CustomerRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CustomerEventListener {

    private final AccountService accountService;

    @EventListener
    public void customerRegistered(CustomerRegisteredEvent registeredEvent) {
        accountService.create(registeredEvent.customerId());
    }

}