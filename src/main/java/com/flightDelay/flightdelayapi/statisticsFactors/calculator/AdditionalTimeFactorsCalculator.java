package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;

import java.util.List;

public interface AdditionalTimeFactorsCalculator {

    double calculateAverageFromList(List<AdditionalTimeDto> allByAirportWithDateAfter);

    ValueWithDateHolder calculateTopMonthDelay(List<AdditionalTimeDto> allByAirportWithDateAfter);
}
