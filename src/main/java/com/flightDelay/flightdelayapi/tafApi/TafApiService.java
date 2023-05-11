package com.flightDelay.flightdelayapi.tafApi;

import com.flightDelay.flightdelayapi.weather.Weather;
import com.flightDelay.flightdelayapi.weather.taf.TafWeather;

public interface TafApiService {

    TafWeather getWeather(String airportIdent);
}
