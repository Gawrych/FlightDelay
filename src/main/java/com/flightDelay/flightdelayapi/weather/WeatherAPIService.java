package com.flightDelay.flightdelayapi.weather;

public interface WeatherAPIService {

    Weather getWeather(String airportIdent, long date);
}
