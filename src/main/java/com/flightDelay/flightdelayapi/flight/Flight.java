package com.flightDelay.flightdelayapi.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("airport_ident")
    private String airportIdent;

    @NonNull
    @DateTimeFormat(pattern = DateProcessor.DATE_WITH_TIME_PATTERN)
    @JsonProperty("date")
    private Date date;

    @EnumValidator(enumClass = FlightPhase.class, message = "Flight phase must be one of: {enumClass}")
    @JsonProperty("flight_phase")
    private FlightPhase phase;
}