package com.flightDelay.flightdelayapi.weatherFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.PrecisionTimeFlight;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactorsPeriod;

import java.util.List;
import java.util.Map;

public interface WeatherFactorService {

    Map<WeatherFactorName, WeatherFactor> getFactorsByHour(PrecisionTimeFlight precisionTimeFlight);

    List<WeatherFactorsPeriod> getFactorsInPeriods(Flight flight, int amountOfDays);
}

