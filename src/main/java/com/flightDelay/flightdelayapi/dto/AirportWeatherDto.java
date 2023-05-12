package com.flightDelay.flightdelayapi.dto;

import com.flightDelay.flightdelayapi.shared.FlightPhase;
import com.flightDelay.flightdelayapi.weather.Weather;

import java.util.List;

public record AirportWeatherDto (

    String airportIdent,

    Weather weather,

    int elevationM,

    FlightPhase phase,

    List<RunwayDto> runwaysDTO

) {}

