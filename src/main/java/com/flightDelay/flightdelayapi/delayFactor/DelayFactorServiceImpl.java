package com.flightDelay.flightdelayapi.delayFactor;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.creator.AirportWeatherCreator;
import com.flightDelay.flightdelayapi.weatherFactors.service.WeatherFactorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DelayFactorServiceImpl implements DelayFactorService {

    @Value("${date.defaultPattern}")
    private String defaultDatePattern;

    private final WeatherFactorService weatherFactorService;

    private final AirportWeatherCreator airportWeatherCreator;


    @Override
    public List<DelayFactor> getFactorsByHour(Flight flight) {
        AirportWeatherDto airportWeatherDto = airportWeatherCreator.mapBySingleHour(flight);

        List<DelayFactor> delayFactors = new ArrayList<>();
        delayFactors.addAll(weatherFactorService.getWeatherFactors(airportWeatherDto));

        return delayFactors;
    }

    @Override
    public List<DelayFactorsPeriod> getFactorsInPeriods(Flight flight) {
        List<AirportWeatherDto> airportWeatherForNextDayInPeriod = airportWeatherCreator.mapAllNextDayInPeriods(flight);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(defaultDatePattern);

        return airportWeatherForNextDayInPeriod.stream().map(airportWeatherPeriod -> {
            LocalDateTime hourOfPeriod = LocalDateTime.parse(airportWeatherPeriod.getWeather().getTime());

            return new DelayFactorsPeriod (
                    hourOfPeriod.minusHours(1).format(formatter),
                    hourOfPeriod.plusHours(2).format(formatter),
                    weatherFactorService.getWeatherFactors(airportWeatherPeriod));
        }).toList();
    }
}
