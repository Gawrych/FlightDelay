package com.flightDelay.flightdelayapi.weatherFactors.calculator;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;

public interface InstrumentLandingSystemCalculator {

    IlsCategory getMinRequiredCategory(AirportWeatherDto airportWeatherDto);
}
