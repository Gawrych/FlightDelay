package com.flightDelay.flightdelayapi.weatherFactors.calculators;

import com.flightDelay.flightdelayapi.weatherFactors.dtos.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;

public interface InstrumentLandingSystemCalculator {

    IlsCategory getMinRequiredCategory(AirportWeatherDto airportWeatherDto);
}
