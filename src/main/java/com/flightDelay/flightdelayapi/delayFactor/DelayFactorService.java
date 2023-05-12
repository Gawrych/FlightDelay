package com.flightDelay.flightdelayapi.delayFactor;

import com.flightDelay.flightdelayapi.shared.Flight;

import java.util.List;

public interface DelayFactorService {

    List<DelayFactor> getFactorsByHour(Flight flight);

    List<DelayFactorsPeriod> getFactorsInPeriods(Flight flight);
}

