package com.flightDelay.flightdelayapi.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.deserializer.FlightPhaseEnumDeserializer;
import com.flightDelay.flightdelayapi.shared.enums.FlightPhase;
import com.flightDelay.flightdelayapi.shared.validator.AirportIcaoCodeValidator;
import com.flightDelay.flightdelayapi.shared.validator.EnumValidator;
import jakarta.validation.constraints.NotNull;

public record Flight (
    @AirportIcaoCodeValidator
    String airportIdent,

    @NotNull(message = "{error.message.nullOnPhaseField}")
    @JsonDeserialize(using = FlightPhaseEnumDeserializer.class)
    @EnumValidator(enumClass = FlightPhase.class, message = "{error.message.incorrectPhase}")
    FlightPhase phase
){}