package com.flightDelay.flightdelayapi.weatherFactors.services;

import com.flightDelay.flightdelayapi.delayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;

import java.util.List;

public interface WeatherFactorService {

    List<DelayFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto);
}
