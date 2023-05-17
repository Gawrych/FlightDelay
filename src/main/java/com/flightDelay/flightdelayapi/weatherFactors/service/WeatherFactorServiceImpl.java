package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.PrecisionTimeFlight;
import com.flightDelay.flightdelayapi.weatherFactors.collector.WeatherFactorCollector;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.creator.AirportWeatherCreator;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactorsPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WeatherFactorServiceImpl implements WeatherFactorService {

    @Value("${date.defaultPattern}")
    private String defaultDatePattern;

    private final WeatherFactorCollector weatherFactorCollector;

    private final AirportWeatherCreator airportWeatherCreator;


    @Override
    public List<WeatherFactor> getFactorsByHour(PrecisionTimeFlight precisionTimeFlight) {
        AirportWeatherDto airportWeatherDto = airportWeatherCreator.mapSingleHour(precisionTimeFlight);
        return weatherFactorCollector.getWeatherFactors(airportWeatherDto);
    }

    @Override
    public List<WeatherFactorsPeriod> getFactorsInPeriods(Flight flight, int amountOfDays) {
        List<AirportWeatherDto> airportWeatherInPeriod = airportWeatherCreator.mapPeriods(flight, amountOfDays);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDatePattern);

        return airportWeatherInPeriod.stream().map(airportWeatherPeriod -> {
            LocalDateTime hourOfPeriod = LocalDateTime.parse(airportWeatherPeriod.getWeather().getTime());

            return new WeatherFactorsPeriod(
                    hourOfPeriod.minusHours(1).format(formatter),
                    hourOfPeriod.plusHours(2).format(formatter),
                    weatherFactorCollector.getWeatherFactors(airportWeatherPeriod));
        }).toList();
    }
}
