package com.flightDelay.flightdelayapi.weather;

public interface InstrumentLandingSystemCalculator {

    int getCategory(Weather weather, int elevation);
}
