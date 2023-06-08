package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;

import java.util.List;

public interface AdditionalTimeFactorCollector {
    List<PrecisionFactor> collect(String airportIdent);
}
