package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;

import java.util.List;

public interface TrafficFactorCollector {

    List<PrecisionReport> collect(String airportCode);
}
