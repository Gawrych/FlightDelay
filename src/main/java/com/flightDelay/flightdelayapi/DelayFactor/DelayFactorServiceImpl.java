package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.weather.AirportWeatherService;
import com.flightDelay.flightdelayapi.weather.WeatherFactorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DelayFactorServiceImpl implements DelayFactorService {

    private final WeatherFactorService weatherFactorService;

    private final AirportWeatherService airportWeatherService;


    @Override
    public List<DelayFactor> getFactorsByHour(Flight flight) {
        AirportWeatherDto airportWeatherDto = airportWeatherService.getAirportWeather(flight);

        List<DelayFactor> delayFactors = new ArrayList<>();
        delayFactors.addAll(weatherFactorService.getWeatherFactors(airportWeatherDto, flight));

        return delayFactors;
    }
}
