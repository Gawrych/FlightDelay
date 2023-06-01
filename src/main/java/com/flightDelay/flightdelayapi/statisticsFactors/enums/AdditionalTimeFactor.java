package com.flightDelay.flightdelayapi.statisticsFactors.enums;


import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdditionalTimeFactor implements EntityStatisticFactor {

    TOP_MONTH_DELAY_IN_TAXI_IN_AND_ASMA("min", StatisticFactorType.TOP_VALUE_WITH_DATE, FlightPhase.ARRIVAL),

    TOP_MONTH_DELAY_IN_TAXI_OUT("min", StatisticFactorType.TOP_VALUE_WITH_DATE, FlightPhase.DEPARTURE),

    AVERAGE_DELAY_IN_TAXI_IN_AND_ASMA("min", StatisticFactorType.AVERAGE, FlightPhase.ARRIVAL),

    AVERAGE_TOP_MONTH_DELAY_IN_TAXI_OUT("min", StatisticFactorType.AVERAGE, FlightPhase.DEPARTURE);

    private final String unit;

    private final StatisticFactorType type;

    private final FlightPhase phase;
}
