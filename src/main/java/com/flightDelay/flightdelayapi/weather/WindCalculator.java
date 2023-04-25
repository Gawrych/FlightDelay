package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;

public interface WindCalculator {

    int getCrossWind(AirportWeatherDto airportWeatherDto);

    int getTailwind(AirportWeatherDto airportWeatherDto);
}
