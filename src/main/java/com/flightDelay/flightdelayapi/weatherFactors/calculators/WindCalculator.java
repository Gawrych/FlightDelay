package com.flightDelay.flightdelayapi.weatherFactors.calculators;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;

public interface WindCalculator {

    int getCrossWind(AirportWeatherDto airportWeatherDto);

    int getTailwind(AirportWeatherDto airportWeatherDto);
}
