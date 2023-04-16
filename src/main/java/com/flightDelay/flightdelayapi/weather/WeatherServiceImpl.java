package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.flight.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherAPIServiceImpl weatherAPIService;
    private final LandingWeatherServiceImpl landingWeatherService;

    public List<DelayFactor> getWeatherFactors(Flight flight) {
        Weather departureWeather = weatherAPIService.getWeather(flight.getDepartureAirportIdent(), flight.getDepartureDate());
        Weather arrivalWeather = weatherAPIService.getWeather(flight.getArrivalAirportIdent(), flight.getArrivalDate());

        return new ArrayList<>();
    }
}
