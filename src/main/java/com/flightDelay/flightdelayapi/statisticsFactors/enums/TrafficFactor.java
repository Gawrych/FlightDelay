package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.shared.enums.FactorUnit;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrafficFactor implements EntityStatisticFactor {

    AVERAGE_MONTHLY_DEPARTURES_TRAFFIC(FactorUnit.NUMBER, StatisticFactorType.AVERAGE, FlightPhase.DEPARTURE),

    AVERAGE_MONTHLY_ARRIVAL_TRAFFIC(FactorUnit.NUMBER, StatisticFactorType.AVERAGE, FlightPhase.ARRIVAL);

    private final FactorUnit unit;

    private final StatisticFactorType type;

    private final FlightPhase phase;
}