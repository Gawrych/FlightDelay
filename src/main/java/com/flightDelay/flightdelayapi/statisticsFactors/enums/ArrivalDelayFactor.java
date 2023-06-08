package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.shared.enums.FactorUnit;
import com.flightDelay.flightdelayapi.shared.enums.FlightPhase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArrivalDelayFactor implements EntityStatisticFactor {

    MOST_COMMON_DELAY_CAUSE(FactorUnit.TEXT_WITH_NUMBER, StatisticFactorType.LIST_OF_VALUES_WITH_TEXT, FlightPhase.ARRIVAL),

    AVERAGE_TIME_TO_PARTICULAR_DELAY_CAUSE(FactorUnit.TEXT_WITH_MINUTES, StatisticFactorType.LIST_OF_VALUES_WITH_TEXT, FlightPhase.ARRIVAL);

    private final FactorUnit unit;

    private final StatisticFactorType type;

    private final FlightPhase phase;
}
