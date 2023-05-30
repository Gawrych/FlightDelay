package com.flightDelay.flightdelayapi.statisticsFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.statisticsFactors.model.StatisticsData;

import java.util.List;

public interface StatisticFactorService {

    List<StatisticsData> getFactorsByPhase(Flight flight);
}
