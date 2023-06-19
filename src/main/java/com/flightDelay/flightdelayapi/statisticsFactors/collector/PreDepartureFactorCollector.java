package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;

import java.util.List;

public interface PreDepartureFactorCollector {
    List<PrecisionReport> collect(String airportIdent);
}
