package com.flightDelay.flightdelayapi.weatherFactors.calculator;

@FunctionalInterface
public interface WindFormula {

    double formula(float windSpeed, int windDirection, int runwayHeadingDeg);
}
