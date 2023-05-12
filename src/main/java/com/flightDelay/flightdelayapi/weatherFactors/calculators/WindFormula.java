package com.flightDelay.flightdelayapi.weatherFactors.calculators;

@FunctionalInterface
public interface WindFormula {

    double formula(float windSpeed, int windDirection, int runwayHeadingDeg);
}
