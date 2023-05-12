package com.flightDelay.flightdelayapi.weatherFactors.calculators;

import com.flightDelay.flightdelayapi.weatherFactors.dtos.AirportWeatherDto;

public interface RunwayWeatherCalculator {

    int calculateRunwayVisualRange(float visibility, boolean isDay);

    int calculateCloudBase(AirportWeatherDto airportWeatherDto, int elevationMeters);
}
