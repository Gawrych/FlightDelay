package com.flightDelay.flightdelayapi.weatherFactors.dtos;

import com.flightDelay.flightdelayapi.runway.RunwayDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import com.flightDelay.flightdelayapi.weatherFactors.models.Weather;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportWeatherDto {

    private String airportIdent;

    private Weather weather;

    private int elevationM;

    private FlightPhase phase;

    private List<RunwayDto> runwaysDTO;
}