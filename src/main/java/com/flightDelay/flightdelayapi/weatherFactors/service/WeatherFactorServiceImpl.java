package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.PrecisionTimeFlight;
import com.flightDelay.flightdelayapi.weatherFactors.collector.WeatherFactorCollector;
import com.flightDelay.flightdelayapi.weatherFactors.creator.AirportWeatherCreator;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactorsPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class WeatherFactorServiceImpl implements WeatherFactorService {

    @Value("${date.defaultDateWithTimePattern}")
    private String defaultDateWithTimePattern;

    @Value("${weather.periods.amountOfHoursInOnePeriod}")
    private int amountOfHoursInOnePeriod;

    private final WeatherFactorCollector weatherFactorCollector;

    private final AirportWeatherCreator airportWeatherCreator;

    private final WeatherAPIService weatherAPIService;

    @Override
    public Map<WeatherFactorName, WeatherFactor> getFactorsByHour(PrecisionTimeFlight precisionTimeFlight) {
        Weather weather = weatherAPIService.getWeather(
                precisionTimeFlight.flight().airportIdent(),
                precisionTimeFlight.date());

        AirportWeatherDto airportWeatherDto = airportWeatherCreator.mapSingleHour(precisionTimeFlight.flight(), weather);

        return weatherFactorCollector.getWeatherFactors(airportWeatherDto);
    }

    @Override
    public List<WeatherFactorsPeriod> getFactorsInPeriods(Flight flight, int amountOfDays) {
        List<Weather> weatherInPeriods = weatherAPIService.getWeatherPeriods(flight.airportIdent(), amountOfDays);
        List<AirportWeatherDto> airportWeatherInPeriod = airportWeatherCreator.mapPeriods(flight, weatherInPeriods);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDateWithTimePattern);

        return airportWeatherInPeriod
                .stream()
                .map(airportWeatherPeriod -> createWeatherPeriod(airportWeatherPeriod, formatter))
                .toList();
    }

    private WeatherFactorsPeriod createWeatherPeriod(AirportWeatherDto airportWeatherPeriod,
                                                     DateTimeFormatter formatter) {

        LocalDateTime startTime = LocalDateTime.parse(airportWeatherPeriod.getWeather().getTime());

        LocalDateTime endTime = startTime.plusHours(amountOfHoursInOnePeriod);

        return new WeatherFactorsPeriod(
                startTime.format(formatter),
                endTime.format(formatter),
                weatherFactorCollector.getWeatherFactors(airportWeatherPeriod));
    }
}
