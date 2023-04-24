package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;

public interface WeatherCalculator {

    int getCrossWind(AirportWeatherDto airportWeatherDto);

    int getTailwind(AirportWeatherDto airportWeatherDto);

    int calculateRunwayVisualRange(float visibility, boolean isDay);

    int calculateCloudBase(float temperature, float dewPoint, int elevation);
}
