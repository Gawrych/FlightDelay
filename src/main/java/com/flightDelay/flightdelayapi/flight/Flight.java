package com.flightDelay.flightdelayapi.flight;

import com.flightDelay.flightdelayapi.enumValidation.EnumValidator;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
public class Flight {

    @NonNull
    private String airportIdent;

    @NonNull
    @DateTimeFormat(pattern = DateProcessor.DATE_WITH_TIME_PATTERN)
    private Date date;

    @EnumValidator(enumClass = FlightPhase.class, message = "Flight phase must be one of: {enumClass}")
    private FlightPhase phase;
}