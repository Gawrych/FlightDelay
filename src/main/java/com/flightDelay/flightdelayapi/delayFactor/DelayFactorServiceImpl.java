package com.flightDelay.flightdelayapi.delayFactor;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.creators.AirportWeatherCreator;
import com.flightDelay.flightdelayapi.weatherFactors.services.WeatherFactorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.flightDelay.flightdelayapi.dateProcessor.DateProcessorImpl.DATE_WITH_TIME_PATTERN;

@Service
@RequiredArgsConstructor
public class DelayFactorServiceImpl implements DelayFactorService {

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_WITH_TIME_PATTERN);

        return airportWeatherForNextDayInPeriod.stream().map(airportWeatherPeriod -> {
            LocalDateTime hourOfPeriod = LocalDateTime.parse(airportWeatherPeriod.weather().getTime());

            return new DelayFactorsPeriod (
                    hourOfPeriod.minusHours(1).format(formatter),
                    hourOfPeriod.plusHours(2).format(formatter),
                    weatherFactorService.getWeatherFactors(airportWeatherPeriod));
        }).toList();
    }
}
