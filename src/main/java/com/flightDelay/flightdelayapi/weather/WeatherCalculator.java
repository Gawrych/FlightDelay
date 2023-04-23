package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.shared.IlsCategory;

import java.util.Map;

public interface WeatherCalculator {

    int getCrossWind(AirportWeatherDto airportWeatherDto);

    int getHeadWind(AirportWeatherDto airportWeatherDto);

    int calculateRunwayVisualRange(float visibility, boolean isDay);

    int calculateCloudBase(float temperature, float dewPoint, int elevation);
}
