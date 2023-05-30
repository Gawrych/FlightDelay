package com.flightDelay.flightdelayapi.statisticsFactors.enums;

public interface StatisticFactorName {

//    PERCENTAGE_OF_DELAYS_LAST_YEAR("%", ),
//
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

    String getUnit();

    StatisticFactorType getType();

    String name();

    String getDelayPhase();
}
