package com.flightDelay.flightdelayapi.statisticsFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;

import java.util.Map;

public interface StatisticFactorService {

    Map<String, PrecisionFactor> getFactorsByPhase(Flight flight);
}
