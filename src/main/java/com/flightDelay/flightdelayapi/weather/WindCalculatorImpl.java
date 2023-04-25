package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
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
        return calculateWindSpeedByRadian(airportWeatherDto, (windSpeed, windDirection, runwayHeadingDeg) ->
                windSpeed * sin(toRadians(runwayHeadingDeg - windDirection)));
    }

    @Override
    public int getTailwind(AirportWeatherDto airportWeatherDto) {
        return calculateWindSpeedByRadian(airportWeatherDto, (windSpeed, windDirection, runwayHeadingDeg) ->
                windSpeed * cos(toRadians(runwayHeadingDeg - windDirection)));
    }

    private int calculateWindSpeedByRadian(AirportWeatherDto airportWeatherDto, WindFormula windFormula) {
        int windDirection = airportWeatherDto.getWindDirection();
        float windSpeed = airportWeatherDto.getWindSpeed();

        List<Integer> crosswindSpeedByRunway = new ArrayList<>();

        for (RunwayDto runwayDto : airportWeatherDto.getRunwaysDTO()) {
            crosswindSpeedByRunway.add(BigDecimal.valueOf(
                            windFormula.formula(windSpeed, windDirection, runwayDto.getHeHeadingDegT()))
                    .setScale(0, RoundingMode.UP)
                    .abs()
                    .intValue());
        }

        //TODO: Exception; Create custom exception and change type of this exception
        return crosswindSpeedByRunway.stream().max(Integer::compare)
                .orElseThrow(() ->  new IllegalStateException("Unable to calculate wind speed"));
    }
}
