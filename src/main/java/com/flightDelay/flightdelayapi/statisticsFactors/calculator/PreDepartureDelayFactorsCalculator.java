package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;

import java.util.List;

public interface PreDepartureDelayFactorsCalculator {

    double calculateAverageDelayTime(List<PreDepartureDelayDto> preDepartureDelays);

    ValueWithDateHolder calculateTopDayDelay(List<PreDepartureDelayDto> preDepartureDelays);

    ValueWithDateHolder calculateTopMonthDelay(List<PreDepartureDelayDto> preDepartureDelays);
}
