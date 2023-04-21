package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;

import java.util.List;

public interface FlightPhasesWeatherService {

    void setWeather(Weather weather);

    void setElevation(int elevation);

    List<DelayFactor> getFactors();
}
