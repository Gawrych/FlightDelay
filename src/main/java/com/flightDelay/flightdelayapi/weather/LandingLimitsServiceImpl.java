package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import org.springframework.stereotype.Service;

@Service
public class LandingLimitsServiceImpl implements LandingLimitsService {

    private final static int CROSSWIND_LIMITS_WITH_ILS_1 = 15;

    public int checkCrossWindLimits(int crossWind, int ilsCategory) {
        return DelayFactor.INFLUENCE_LOW;
    }
}
