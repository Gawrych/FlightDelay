package com.flightDelay.flightdelayapi.weather;

import java.util.Date;
import java.util.List;

public interface WeatherAPIService {

    Weather getWeather(String airportIdent, Date date);

    List<Weather> getAllNextDayWeatherInPeriods(String airportIdent);

}
