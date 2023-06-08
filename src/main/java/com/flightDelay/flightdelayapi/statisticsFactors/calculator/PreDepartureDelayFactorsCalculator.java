package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface PreDepartureDelayFactorsCalculator {

    double calculateAverageDelayTime(@NotEmpty List<PreDepartureDelayDto> preDepartureDelays);

    ValueWithDateHolder calculateTopDayDelay(@NotEmpty List<PreDepartureDelayDto> preDepartureDelays);

    ValueWithDateHolder calculateTopMonthDelay(@NotEmpty List<PreDepartureDelayDto> preDepartureDelays);
}
