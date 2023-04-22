package com.flightDelay.flightdelayapi.weather;
import com.flightDelay.flightdelayapi.DelayFactor.FactorName;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.flight.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WeatherCalculatorImpl implements WeatherCalculator {

    private final InstrumentLandingSystemCalculator instrumentLandingSystemCalculator;

    public Map<FactorName, Integer> getConditions(Flight flight) {
        return new HashMap<>();
//        return Map.of(
//                FactorName.CROSSWIND, getCrossWind(airport, weather),
//                FactorName.HEADWIND, getHeadWind(airport, weather));
    }

    public int getIlsCategory(Airport airport, Weather weather) {
        return instrumentLandingSystemCalculator.getCategory(weather, airport.getElevationFt());
    }

    public int getCrossWind(Airport airport, Weather weather) {
        return 0;
    }

    public int getHeadWind(Airport airport, Weather weather) {
        return 0;
    }
}
