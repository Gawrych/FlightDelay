package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherAPIService {

    Weather getWeather(String airportIdent, LocalDateTime date);

    List<Weather> getWeatherPeriods(String airportIdent, int amountOfDays);

}
