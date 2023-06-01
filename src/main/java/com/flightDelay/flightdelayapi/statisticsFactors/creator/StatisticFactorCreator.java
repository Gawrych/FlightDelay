package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;

public interface StatisticFactorCreator {

    PrecisionFactor createAverage(EntityStatisticFactor factorName,
                                  double value);

    PrecisionFactor createTopValue(EntityStatisticFactor factorName,
                                   ValueWithDateHolder value);

    PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName);
}
