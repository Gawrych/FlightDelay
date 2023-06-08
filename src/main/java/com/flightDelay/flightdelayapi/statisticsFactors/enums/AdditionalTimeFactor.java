package com.flightDelay.flightdelayapi.statisticsFactors.enums;


import com.flightDelay.flightdelayapi.shared.enums.FactorUnit;
import com.flightDelay.flightdelayapi.shared.enums.FlightPhase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdditionalTimeFactor implements EntityStatisticFactor {

    TOP_MONTH_DELAY_IN_TAXI_IN_AND_ASMA(FactorUnit.MINUTES, StatisticFactorType.TOP_VALUE_WITH_DATE, FlightPhase.ARRIVAL),

    TOP_MONTH_DELAY_IN_TAXI_OUT(FactorUnit.MINUTES, StatisticFactorType.TOP_VALUE_WITH_DATE, FlightPhase.DEPARTURE),

    AVERAGE_DELAY_IN_TAXI_IN_AND_ASMA(FactorUnit.MINUTES, StatisticFactorType.AVERAGE, FlightPhase.ARRIVAL),

    AVERAGE_DELAY_IN_TAXI_OUT(FactorUnit.MINUTES, StatisticFactorType.AVERAGE, FlightPhase.DEPARTURE);

    private final FactorUnit unit;

    private final StatisticFactorType type;

    private final FlightPhase phase;
}
