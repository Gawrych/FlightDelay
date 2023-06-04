package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;

import java.util.List;

public interface ArrivalDelayFactorsCalculator {

    ValueWithTextHolder calculateMostCommonDelayCause(List<ArrivalDelayDto> additionalTimes);
}
