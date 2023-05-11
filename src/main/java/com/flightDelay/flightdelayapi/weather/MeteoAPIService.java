package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.weather.meteo.MeteoWeather;

public interface MeteoAPIService {

    MeteoWeather getWeather(String airportIdent, long date);
}
