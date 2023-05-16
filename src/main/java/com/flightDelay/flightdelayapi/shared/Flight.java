package com.flightDelay.flightdelayapi.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.deserializer.DateInPatternDeserializer;
import com.flightDelay.flightdelayapi.shared.deserializer.FlightPhaseEnumDeserializer;
import com.flightDelay.flightdelayapi.shared.validator.AirportIcaoCodeValidator;
import com.flightDelay.flightdelayapi.shared.validator.EnumValidator;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.NonNull;

import java.time.LocalDateTime;

public record Flight (
        @AirportIcaoCodeValidator
        String airportIdent,

        @NonNull
        @JsonDeserialize(using = DateInPatternDeserializer.class)
        LocalDateTime date,

        @NonNull
        @JsonDeserialize(using = FlightPhaseEnumDeserializer.class)
        @EnumValidator(enumClass = FlightPhase.class, message = "{error.message.incorrectPhase}")
        FlightPhase phase) {}