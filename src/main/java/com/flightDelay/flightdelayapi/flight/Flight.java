package com.flightDelay.flightdelayapi.flight;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class Flight {

    @JsonProperty("departure_airport_ident")
    private String departureAirportIdent;

    @JsonProperty("departure_date")
    private long departureDate;

    @JsonProperty("arrival_airport_ident")
    private String arrivalAirportIdent;

    @JsonProperty("arrival_date")
    private long arrivalDate;
}