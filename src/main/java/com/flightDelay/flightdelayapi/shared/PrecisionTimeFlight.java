package com.flightDelay.flightdelayapi.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.deserializer.DateInPatternDeserializer;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PrecisionTimeFlight (
        Flight flight,
        @NotNull(message = "{error.message.nullOnDateField}")
        @JsonDeserialize(using = DateInPatternDeserializer.class)
        LocalDateTime date
){}