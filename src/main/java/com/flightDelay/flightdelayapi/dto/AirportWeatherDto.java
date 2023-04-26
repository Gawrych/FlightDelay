package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.shared.FlightPhase;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class AirportWeatherDto {

    @NonNull
    private String airportIdent;

    private boolean isDay;

    private float temperature;

    private float dewPoint;

    private float visibility;

    private float windSpeed;

    private int windDirection;

    private float windGusts;

    private float rain;

    @NonNull
    private FlightPhase phase;

    @NonNull
    private List<RunwayDto> runwaysDTO;
}
