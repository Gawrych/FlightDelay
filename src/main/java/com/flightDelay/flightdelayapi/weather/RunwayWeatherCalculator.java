package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;

public interface RunwayWeatherCalculator {

    int calculateRunwayVisualRange(float visibility, boolean isDay);

    int calculateCloudBaseAboveRunway(AirportWeatherDto airportWeatherDto, int elevationMeters);
}
