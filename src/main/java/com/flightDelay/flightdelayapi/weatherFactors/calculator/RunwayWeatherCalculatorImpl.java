package com.flightDelay.flightdelayapi.weatherFactors.calculator;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
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
    public int calculateCloudBase(float temperature, float dewPoint, int elevation) {
        return Math.round(((temperature - dewPoint) / 10) * 1247 + elevation);
    }
}
