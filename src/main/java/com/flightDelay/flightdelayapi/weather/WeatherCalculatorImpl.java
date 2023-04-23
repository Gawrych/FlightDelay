package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherCalculatorImpl implements WeatherCalculator {

    public int getCrossWind(AirportWeatherDto airportWeatherDto) {
        return 23;
    }

    public int getHeadWind(AirportWeatherDto airportWeatherDto) {
        return 20;
    }

    public int calculateRunwayVisualRange(float visibility, boolean isDay) {
        double multiplier = 1.6;
        if (isDay) {
            multiplier = 1.3;
        }
        return (int) Math.round((visibility * multiplier));
    }

    public int calculateCloudBase(float temperature, float dewPoint, int elevation) {
        return Math.round((temperature - dewPoint) / 10 * 1247 + elevation);
    }
}
