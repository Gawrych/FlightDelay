package com.flightDelay.flightdelayapi.statisticsFactors.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdditionalTimeFactors implements StatisticFactorName {

    TOP_MONTH("min", StatisticFactorType.TOP_MONTH, "ADDITIONAL_TIME"),

    AVERAGE("min", StatisticFactorType.AVERAGE, "ADDITIONAL_TIME");

    private final String unit;

    private final StatisticFactorType type;

    private final String delayPhase;
}
