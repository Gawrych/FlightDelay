package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.shared.enums.FactorUnit;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrafficFactor implements EntityStatisticFactor {

    TOP_MONTH_OF_TRAFFIC(FactorUnit.TEXT_WITH_NUMBER, StatisticFactorType.TOP_VALUE_WITH_DATE, FlightPhase.DEPARTURE_AND_ARRIVAL),

    AVERAGE_MONTHLY_TRAFFIC(FactorUnit.NUMBER, StatisticFactorType.AVERAGE, FlightPhase.DEPARTURE_AND_ARRIVAL);

    private final FactorUnit unit;

    private final StatisticFactorType type;

    private final FlightPhase phase;
}