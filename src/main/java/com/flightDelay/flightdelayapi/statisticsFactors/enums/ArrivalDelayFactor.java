package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.shared.enums.FactorUnit;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArrivalDelayFactor implements EntityStatisticFactor {

//    PERCENTAGE_OF_DELAYS_ABOVE_15_LAST_YEAR("%"),
//
//    PERCENTAGE_OF_DELAYS_LAST_MONTHS("%"),
//
//    PERCENTAGE_OF_DELAYS_ABOVE_15_LAST_MONTHS("%"),
//
//    AVERAGE_DELAY_TIME("min"),
//
//    MOST_COMMON_CAUSE("text"),
//
//    LAST_FEW_DELAY_EVENTS("text");


    MOST_COMMON_DELAY_CAUSE(FactorUnit.TEXT, StatisticFactorType.VALUE_WITH_TEXT, FlightPhase.ARRIVAL);

    private final FactorUnit unit;

    private final StatisticFactorType type;

    private final FlightPhase phase;
}
