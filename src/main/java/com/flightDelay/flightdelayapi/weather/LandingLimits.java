package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.FactorInfluence;
import com.flightDelay.flightdelayapi.DelayFactor.FactorName;
import com.flightDelay.flightdelayapi.flight.FlightPhase;

public interface LandingLimits {

    FactorInfluence checkLimits(FactorName name, int value);
}
