package com.flightDelay.flightdelayapi.weather;

public interface LandingLimitsService {

    int checkCrossWindLimits(int crossWind, int ilsCategory);
}
