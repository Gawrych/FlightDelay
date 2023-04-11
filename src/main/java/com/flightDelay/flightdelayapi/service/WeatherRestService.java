package com.flightDelay.flightdelayapi.service;

import com.flightDelay.flightdelayapi.model.WeatherDetails;
import org.springframework.stereotype.Service;

@Service
public interface WeatherRestService {

    WeatherDetails getWeatherDetails();
}
