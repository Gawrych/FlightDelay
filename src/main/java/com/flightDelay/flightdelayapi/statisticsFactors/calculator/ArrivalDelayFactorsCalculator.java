package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface ArrivalDelayFactorsCalculator {

    List<ValueWithTextHolder> calculateMostCommonDelayCause(@NotEmpty List<ArrivalDelayDto> additionalTimes);

    List<ValueWithTextHolder> calculateAverageTimeToParticularDelayCause(@NotEmpty List<ArrivalDelayDto> additionalTimes);
}
