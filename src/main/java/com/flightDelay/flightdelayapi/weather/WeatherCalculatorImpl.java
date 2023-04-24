package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Component
@RequiredArgsConstructor
public class WeatherCalculatorImpl implements WeatherCalculator {

    public int getCrossWind(AirportWeatherDto airportWeatherDto) {
        float windSpeed = airportWeatherDto.getWindSpeed();
        int windDirection = airportWeatherDto.getWindDirection();

        List<Integer> crosswindSpeed = new ArrayList<>();

        for (RunwayDto runwayDto : airportWeatherDto.getRunwaysDTO()) {
           crosswindSpeed.add(
                   new BigDecimal(windSpeed * sin(toRadians(runwayDto.getHeHeadingDegT() - windDirection)))
                            .setScale(0, RoundingMode.UP)
                            .abs()
                            .intValue());
        }
        crosswindSpeed.forEach(System.out::println);

        //TODO: Exception; Create custom exception and change type of this exception
        return crosswindSpeed.stream().min(Integer::compare).orElseThrow(() ->  new IllegalStateException("Unable to calculate crosswind"));
    }

    public int getTailwind(AirportWeatherDto airportWeatherDto) {
        float windSpeed = airportWeatherDto.getWindSpeed();
        int windDirection = airportWeatherDto.getWindDirection();

        System.out.println(airportWeatherDto.getWindSpeed());
        System.out.println(airportWeatherDto.getWindDirection());

        List<Integer> crosswindSpeed = new ArrayList<>();

        for (RunwayDto runwayDto : airportWeatherDto.getRunwaysDTO()) {
            System.out.println(runwayDto.getHeHeadingDegT());
            crosswindSpeed.add(
                    new BigDecimal(windSpeed * cos(toRadians(runwayDto.getHeHeadingDegT() - windDirection)))
                            .setScale(0, RoundingMode.UP)
                            .abs()
                            .intValue());
        }

        crosswindSpeed.forEach(System.out::println);

        //TODO: Exception; Create custom exception and change type of this exception
        return crosswindSpeed.stream().min(Integer::compare).orElseThrow(() ->  new IllegalStateException("Unable to calculate crosswind"));
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
