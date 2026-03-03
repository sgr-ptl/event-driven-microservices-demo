package com.example.userservice.event;

import java.time.Instant;
import java.util.UUID;

public record UserCreatedEvent(
        UUID userId,
        String name,
        String email,
        Instant createdAt
) {}