package com.flightDelay.flightdelayapi.statisticsFactors.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdditionalTimeFactors implements StatisticFactorName {

    TOP_MONTH_ARRIVAL("min", StatisticFactorType.TOP_MONTH, "ADDITIONAL_TIME"),

    TOP_MONTH_DEPARTURE("min", StatisticFactorType.TOP_MONTH, "ADDITIONAL_TIME"),

    AVERAGE_ARRIVAL("min", StatisticFactorType.AVERAGE, "ADDITIONAL_TIME"),

    AVERAGE_DEPARTURE("min", StatisticFactorType.AVERAGE, "ADDITIONAL_TIME");


    private final String unit;

    private final StatisticFactorType type;

    private final String delayPhase;
}
