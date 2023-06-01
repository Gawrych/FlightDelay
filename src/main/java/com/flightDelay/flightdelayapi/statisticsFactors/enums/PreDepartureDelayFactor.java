package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PreDepartureDelayFactor implements EntityStatisticFactor {

    TOP_MONTH_OF_PRE_DEPARTURE_DELAY("min", StatisticFactorType.TOP_VALUE_WITH_DATE, FlightPhase.DEPARTURE),

    TOP_DAY_OF_PRE_DEPARTURE_DELAY("min", StatisticFactorType.TOP_VALUE_WITH_PRECISION_DATE, FlightPhase.DEPARTURE),

    AVERAGE_PRE_DEPARTURE_DELAY("min", StatisticFactorType.AVERAGE, FlightPhase.DEPARTURE);

    private final String unit;

    private final StatisticFactorType type;

    private final FlightPhase phase;
}
