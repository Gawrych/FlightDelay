package com.flightDelay.flightdelayapi.weatherFactors.services;

import com.flightDelay.flightdelayapi.weatherFactors.models.Weather;

import java.util.Date;
import java.util.List;

public interface WeatherAPIService {

    Weather getWeather(String airportIdent, Date date);

    List<Weather> getAllNextDayWeatherInPeriods(String airportIdent);

}
