package com.flightDelay.flightdelayapi.weatherFactors.dto;

import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import com.flightDelay.flightdelayapi.shared.enums.FlightPhase;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
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

    private List<RunwayWeatherDto> runwaysDTO;
}