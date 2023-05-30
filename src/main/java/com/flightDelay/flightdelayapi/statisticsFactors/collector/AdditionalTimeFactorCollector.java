package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.statisticsFactors.model.StatisticsData;

public interface AdditionalTimeFactorCollector {
    StatisticsData getFactors(Flight flight);
}
