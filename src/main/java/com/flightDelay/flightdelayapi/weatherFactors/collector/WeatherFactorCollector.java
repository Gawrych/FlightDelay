package com.flightDelay.flightdelayapi.weatherFactors.collector;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;

import java.util.Map;

public interface WeatherFactorCollector {

    Map<WeatherFactorName, WeatherFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto);
}
