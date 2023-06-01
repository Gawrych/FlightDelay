package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;

public interface EntityStatisticFactor {

    String getUnit();

    StatisticFactorType getType();

    String name();

    FlightPhase getPhase();
}
