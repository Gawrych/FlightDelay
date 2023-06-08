package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface AdditionalTimeFactorsCalculator {

    double calculateAverageFromList(@NotEmpty List<AdditionalTimeDto> allByAirportWithDateAfter);

    ValueWithDateHolder calculateTopDelayMonth(@NotEmpty List<AdditionalTimeDto> allByAirportWithDateAfter);
}
