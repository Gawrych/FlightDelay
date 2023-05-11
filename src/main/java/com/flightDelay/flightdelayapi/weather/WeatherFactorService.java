package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;

import java.util.List;

public interface WeatherFactorService {

    List<DelayFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto);
}
