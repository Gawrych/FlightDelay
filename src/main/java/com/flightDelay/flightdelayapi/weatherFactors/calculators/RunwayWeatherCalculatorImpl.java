package com.flightDelay.flightdelayapi.weatherFactors.calculators;

import com.flightDelay.flightdelayapi.weatherFactors.dtos.AirportWeatherDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class RunwayWeatherCalculatorImpl implements RunwayWeatherCalculator {

    @Override
    public int calculateRunwayVisualRange(float visibility, boolean isDay) {
        double multiplier = 1.6;
        if (isDay) {
            multiplier = 1.3;
        }
        return (int) Math.round((visibility * multiplier));
    }

    @Override
    public int calculateCloudBase(AirportWeatherDto airportWeatherDto, int elevation) {
        return Math.round(
                (airportWeatherDto.getWeather().getTemperature() - airportWeatherDto.getWeather().getDewPoint()) / 10 * 1247 + elevation);
    }
}
