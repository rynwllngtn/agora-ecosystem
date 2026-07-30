package dev.rynwllngtn.bank.shared.application.event;

import java.util.UUID;

public record CustomerRegisteredEvent(
        UUID customerId
) {}