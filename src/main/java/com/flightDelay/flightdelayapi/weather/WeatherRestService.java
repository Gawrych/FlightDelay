package com.flightDelay.flightdelayapi.weather;

import org.springframework.stereotype.Service;

@Service
public interface WeatherRestService {

    WeatherDetails getWeatherDetails();
}
