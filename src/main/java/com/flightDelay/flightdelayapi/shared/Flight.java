package com.flightDelay.flightdelayapi.shared;

import com.flightDelay.flightdelayapi.enumValidation.EnumValidator;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import java.util.Date;

import static com.flightDelay.flightdelayapi.shared.DateProcessorImpl.DATE_WITH_TIME_PATTERN;

public record Flight (
        @NonNull
        String airportIdent,

        @NonNull
        @DateTimeFormat(pattern = DATE_WITH_TIME_PATTERN)
        Date date,

        //TODO: Handle the exception with custom
        @EnumValidator(enumClass = FlightPhase.class, message = "Flight phase must be one of: {enumClass}")
        FlightPhase phase) {}