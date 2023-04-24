package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.shared.IlsCategory;

public interface InstrumentLandingSystemCalculator {

    IlsCategory getMinRequiredCategory(AirportWeatherDto airportWeatherDto);
}
