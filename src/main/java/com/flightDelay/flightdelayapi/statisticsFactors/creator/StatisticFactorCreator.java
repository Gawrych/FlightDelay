package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;

import java.util.List;

public interface StatisticFactorCreator {

    PrecisionFactor createSimpleValue(EntityStatisticFactor factorName, double value);

    PrecisionFactor createValueWithDate(EntityStatisticFactor factorName, ValueWithDateHolder value);

    PrecisionFactor createListValuesWithText(EntityStatisticFactor factorName, List<ValueWithTextHolder> values);

    PrecisionFactor createNoDataFactor(EntityStatisticFactor factorName);
}
