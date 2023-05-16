package com.flightDelay.flightdelayapi.weatherFactors.collector;

import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;

import java.util.List;

public interface WeatherFactorCollector {

    List<WeatherFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto);
}
