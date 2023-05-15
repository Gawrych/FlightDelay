package com.flightDelay.flightdelayapi.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.enums.deserializer.DateInPatternDeserializer;
import com.flightDelay.flightdelayapi.shared.enums.deserializer.FlightPhaseEnumDeserializer;
import com.flightDelay.flightdelayapi.shared.enums.validator.EnumValidator;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record Flight (
        @NonNull
        @Length(min = 4, max = 4, message = "{error.message.icaoCodeLength}")
        String airportIdent,

        @NonNull
        @JsonDeserialize(using = DateInPatternDeserializer.class)
        LocalDateTime date,

        @NonNull
        @JsonDeserialize(using = FlightPhaseEnumDeserializer.class)
        @EnumValidator(enumClass = FlightPhase.class, message = "{error.message.incorrectPhase}")
        FlightPhase phase) {}