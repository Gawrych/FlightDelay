package com.flightDelay.flightdelayapi.weather;

@FunctionalInterface
public interface WindFormula {

    double formula(float windSpeed, int windDirection, int runwayHeadingDeg);
}
