package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weather.Weather;

import java.util.List;

public interface DelayFactorService {

    List<DelayFactor> getFactorsByHour(Flight flight);

    Weather getFactorsByDay(Flight flight);
}

