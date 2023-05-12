package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weather.period.DelayFactorsPeriod;

import java.util.List;

public interface DelayFactorService {

    List<DelayFactor> getFactorsByHour(Flight flight);

    List<DelayFactorsPeriod> getFactorsInPeriods(Flight flight);
}

