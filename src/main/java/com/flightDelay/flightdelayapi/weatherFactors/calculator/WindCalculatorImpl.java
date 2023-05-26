package com.flightDelay.flightdelayapi.weatherFactors.calculator;

import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.exception.WindSpeedCalculationFailedException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.toRadians;

@Component
@NoArgsConstructor
public class WindCalculatorImpl implements WindCalculator {

    @Override
    public int getCrossWind(AirportWeatherDto airportWeatherDto) {
        WindFormula crosswindFormula = (windSpeed, windDirection, runwayHeadingDeg) ->
                windSpeed * sin(toRadians(runwayHeadingDeg - windDirection));

        return Math.abs(calculateWindSpeedByRadian(airportWeatherDto, crosswindFormula));
    }

    @Override
    public int getTailwind(AirportWeatherDto airportWeatherDto) {
        WindFormula tailWindFormula = (windSpeed, windDirection, runwayHeadingDeg) ->
                windSpeed * cos(toRadians(runwayHeadingDeg - windDirection));

        return calculateWindSpeedByRadian(airportWeatherDto, tailWindFormula);
    }

    private int calculateWindSpeedByRadian(AirportWeatherDto airportWeatherDto, WindFormula windFormula) {
        int windDirection = airportWeatherDto.getWeather().getWindDirection();
        float windSpeed = airportWeatherDto.getWeather().getWindSpeedKn();

        List<Integer> crosswindSpeedByRunway = new ArrayList<>();

        for (RunwayWeatherDto runwayWeatherDto : airportWeatherDto.getRunwaysDTO()) {
            int heHeadingDegResult = BigDecimal
                    .valueOf(windFormula.formula(windSpeed, windDirection, runwayWeatherDto.getHeHeadingDegT()))
                    .setScale(0, RoundingMode.UP)
                    .abs()
                    .intValue();

            crosswindSpeedByRunway.add(heHeadingDegResult);
        }

        return crosswindSpeedByRunway
                .stream()
                .max(Integer::compare)
                .orElseThrow(WindSpeedCalculationFailedException::new);
    }
}
