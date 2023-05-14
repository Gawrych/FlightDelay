package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.flightDelay.flightdelayapi.delayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;

import java.util.List;

public interface WeatherFactorService {

    List<DelayFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto);
}
