package com.flightDelay.flightdelayapi.statisticsFactors.service;

import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;

import java.util.Map;

public interface StatisticFactorService {

    Map<String, PrecisionReport> getFactorsByPhase(String airportIdent);
}
