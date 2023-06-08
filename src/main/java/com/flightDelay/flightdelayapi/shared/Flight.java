package com.flightDelay.flightdelayapi.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.deserializer.FlightPhaseEnumDeserializer;
import com.flightDelay.flightdelayapi.shared.validator.AirportIcaoCodeValidator;
import com.flightDelay.flightdelayapi.shared.validator.EnumValidator;
import com.flightDelay.flightdelayapi.shared.enums.FlightPhase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @AirportIcaoCodeValidator
    private String airportIdent;

    @NotNull(message = "{error.message.nullOnPhaseField}")
    @JsonDeserialize(using = FlightPhaseEnumDeserializer.class)
    @EnumValidator(enumClass = FlightPhase.class, message = "{error.message.incorrectPhase}")
    private FlightPhase phase;
}