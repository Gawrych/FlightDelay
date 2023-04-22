package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.FactorInfluence;
import com.flightDelay.flightdelayapi.DelayFactor.FactorName;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LandingLimitsImpl implements LandingLimits {

    private final static int CROSSWIND_LIMITS_WITH_ILS_1 = 15;

    public FactorInfluence checkLimits(FactorName name, int value) {
        return FactorInfluence.LOW;
    }

    public FactorInfluence checkCrossWindLimits(int crossWind, int ilsCategory) {
        return FactorInfluence.LOW;
    }
}
