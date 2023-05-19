package com.flightDelay.flightdelayapi.weatherFactors.calculator;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;

public interface RunwayWeatherCalculator {

    int calculateRunwayVisualRange(float visibility, boolean isDay);

    int calculateCloudBase(float temperature, float dewPoint, int elevationMeters);
}
