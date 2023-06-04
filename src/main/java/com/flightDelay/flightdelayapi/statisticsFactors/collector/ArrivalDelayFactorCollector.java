package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;

import java.util.List;

public interface ArrivalDelayFactorCollector {

    List<PrecisionFactor> collect(Flight flight);
}
