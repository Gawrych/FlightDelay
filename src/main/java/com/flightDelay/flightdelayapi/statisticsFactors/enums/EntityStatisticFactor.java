package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.shared.enums.FactorUnit;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;

public interface EntityStatisticFactor {

    FactorUnit getUnit();

    StatisticFactorType getType();

    String name();

    FlightPhase getPhase();
}
