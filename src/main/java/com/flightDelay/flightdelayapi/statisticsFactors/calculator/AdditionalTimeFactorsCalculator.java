package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTime;
import com.flightDelay.flightdelayapi.statisticsFactors.model.TopMonthValueHolder;

import java.util.List;

public interface AdditionalTimeFactorsCalculator {

    double calculateAverageFromList(List<AdditionalTime> allByAirportWithDateAfter);

    TopMonthValueHolder calculateTopMonth(List<AdditionalTime> allByAirportWithDateAfter);
}
