package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactorsPeriod;

import java.util.List;

public interface WeatherFactorService {

    List<WeatherFactor> getFactorsByHour(Flight flight);

    List<WeatherFactorsPeriod> getFactorsInPeriods(Flight flight, int amountOfDays);
}

