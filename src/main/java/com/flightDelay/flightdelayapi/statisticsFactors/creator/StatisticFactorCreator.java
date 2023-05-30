package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorName;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.TopMonthValueHolder;

public interface StatisticFactorCreator {

    PrecisionFactor createAverage(StatisticFactorName factorName,
                                  double value);

    PrecisionFactor createTopMonth(StatisticFactorName factorName,
                                 TopMonthValueHolder value);

    PrecisionFactor getNoDataFactor(StatisticFactorName factorName);
}
