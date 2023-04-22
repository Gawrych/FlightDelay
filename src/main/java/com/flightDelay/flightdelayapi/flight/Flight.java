package com.flightDelay.flightdelayapi.flight;

import com.flightDelay.flightdelayapi.enumValidation.EnumValidator;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import java.util.Date;

public record Flight (
        @NonNull
        String airportIdent,

        @NonNull
        @DateTimeFormat(pattern = DateProcessor.DATE_WITH_TIME_PATTERN)
        Date date,

        @EnumValidator(enumClass = FlightPhase.class, message = "Flight phase must be one of: {enumClass}")
        FlightPhase phase) {}