package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;

public interface StatisticFactorCreator {

    PrecisionFactor createSimpleValue(EntityStatisticFactor factorName,
                                      double value);

    PrecisionFactor createValueWithDate(EntityStatisticFactor factorName,
                                        ValueWithDateHolder value);

    PrecisionFactor createValueWithText(EntityStatisticFactor factorName,
                                        ValueWithTextHolder value);

    PrecisionFactor createNoDataFactor(EntityStatisticFactor factorName);
}
