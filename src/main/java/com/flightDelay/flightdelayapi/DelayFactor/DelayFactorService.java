package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.Flight;

import java.util.List;

public interface DelayFactorService {

    List<DelayFactor> getFactorsByHour(Flight flight);
}

