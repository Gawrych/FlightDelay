package com.flightDelay.flightdelayapi.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.deserializer.DateInPatternDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PrecisionTimeFlight extends Flight {

    @NotNull(message = "{error.message.nullOnDateField}")
    @JsonDeserialize(using = DateInPatternDeserializer.class)
    private LocalDateTime date;
}