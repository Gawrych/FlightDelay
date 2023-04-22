package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.FactorName;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.flight.Flight;

import java.util.Map;

public interface WeatherCalculator {

    Map<FactorName, Integer> getConditions(Flight flight);

    int getIlsCategory(Airport airport, Weather weather);

    int getCrossWind(Airport airport, Weather weather);

    int getHeadWind(Airport airport, Weather weather);
}
