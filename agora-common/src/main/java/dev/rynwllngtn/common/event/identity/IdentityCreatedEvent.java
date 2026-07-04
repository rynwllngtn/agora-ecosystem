package dev.rynwllngtn.common.event.identity;

import java.time.LocalDateTime;
import java.util.UUID;

public record IdentityCreatedEvent(
        UUID id,
        String cpf,
        String email,
        LocalDateTime createdAt
) {}