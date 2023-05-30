package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorName;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.TopMonthValueHolder;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;

public interface StatisticFactorCreator {

    PrecisionFactor createAverage(StatisticFactorName factorName,
                                  FlightPhase phase,
                                  double value);

    PrecisionFactor createTopMonth(StatisticFactorName factorName,
                                 FlightPhase phase,
                                 TopMonthValueHolder value);

    PrecisionFactor getNoDataFactor(StatisticFactorName factorName, FlightPhase phase);
}
