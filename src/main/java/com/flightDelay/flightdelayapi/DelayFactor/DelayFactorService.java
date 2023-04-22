package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.flight.Flight;

import java.util.Date;
import java.util.List;

public interface DelayFactorService {

    List<DelayFactor> getFactorsByHour(Flight flight);
}

