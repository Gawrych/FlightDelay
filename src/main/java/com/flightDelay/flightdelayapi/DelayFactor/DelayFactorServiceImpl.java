package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.weather.WeatherServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DelayFactorServiceImpl implements DelayFactorService {

    private final WeatherServiceImpl weatherRestService;

    public List<DelayFactor> calculateFactors(Flight flightToCheck) {
        List<DelayFactor> delayFactors = new ArrayList<>();
        delayFactors.addAll(weatherRestService.getWeatherFactors(flightToCheck));
        return delayFactors;
    }
}
