package dev.rynwllngtn.common.event.identity;

import java.time.LocalDateTime;
import java.util.UUID;

public record IdentityEmailUpdatedEvent(
        UUID id,
        String email,
        LocalDateTime updatedAt
) {}