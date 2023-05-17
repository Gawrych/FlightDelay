package com.flightDelay.flightdelayapi.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.deserializer.DateInPatternDeserializer;
import com.flightDelay.flightdelayapi.shared.deserializer.FlightPhaseEnumDeserializer;
import com.flightDelay.flightdelayapi.shared.validator.AirportIcaoCodeValidator;
import com.flightDelay.flightdelayapi.shared.validator.EnumValidator;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PrecisionTimeFlight extends Flight {

    @NotNull
    @JsonDeserialize(using = DateInPatternDeserializer.class)
    private LocalDateTime date;
}