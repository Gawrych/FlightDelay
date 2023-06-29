package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.PrecisionTimeFlight;
import com.flightDelay.flightdelayapi.weatherFactors.collector.WeatherFactorCollector;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.creator.AirportWeatherCreator;
import com.flightDelay.flightdelayapi.weatherFactors.model.Weather;
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

    @Value("${date.defaultDateWithTimePattern}")
    private String defaultDateWithTimePattern;

    @Value("${weather.periods.amountOfHoursInOnePeriod}")
    private int amountOfHoursInOnePeriod;

    private final WeatherFactorCollector weatherFactorCollector;

    private final AirportWeatherCreator airportWeatherCreator;

    private final WeatherAPIService weatherAPIService;

    @Override
    public List<WeatherFactor> getFactorsByHour(PrecisionTimeFlight precisionTimeFlight) {
        Weather weather = weatherAPIService.getWeather(
                precisionTimeFlight.getAirportIdent(),
                precisionTimeFlight.getDate());

        AirportWeatherDto airportWeatherDto = airportWeatherCreator.mapSingleHour(precisionTimeFlight, weather);

        return weatherFactorCollector.getWeatherFactors(airportWeatherDto);
    }

    @Override
    public List<WeatherFactorsPeriod> getFactorsInPeriods(Flight flight, int amountOfDays) {
        List<Weather> weatherInPeriods = weatherAPIService.getWeatherPeriods(flight.getAirportIdent(), amountOfDays);
        List<AirportWeatherDto> airportWeatherInPeriod = airportWeatherCreator.mapPeriods(flight, weatherInPeriods);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDateWithTimePattern);

        return airportWeatherInPeriod
                .stream()
                .map(airportWeatherPeriod -> createWeatherPeriod(airportWeatherPeriod, formatter))
                .toList();
    }

    private WeatherFactorsPeriod createWeatherPeriod(AirportWeatherDto airportWeatherPeriod,
                                                     DateTimeFormatter formatter) {

        LocalDateTime hourOfPeriod = LocalDateTime.parse(airportWeatherPeriod.getWeather().getTime());

        return new WeatherFactorsPeriod(
                hourOfPeriod.format(formatter),
                hourOfPeriod.plusHours(amountOfHoursInOnePeriod).format(formatter),
                weatherFactorCollector.getWeatherFactors(airportWeatherPeriod));
    }
}
