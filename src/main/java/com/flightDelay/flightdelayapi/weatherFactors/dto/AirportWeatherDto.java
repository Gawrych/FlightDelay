package com.flightDelay.flightdelayapi.weatherFactors.dto;

import com.flightDelay.flightdelayapi.runway.RunwayDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import com.flightDelay.flightdelayapi.weatherFactors.models.Weather;

import java.util.List;

public record AirportWeatherDto (

    String airportIdent,

    Weather weather,

    int elevationM,

    FlightPhase phase,

    List<RunwayDto> runwaysDTO

) {}

