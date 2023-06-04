package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelay;

@FunctionalInterface
public interface ArrivalDelayCauseReference {

    Integer getCause(ArrivalDelay arrivalDelay);
}
