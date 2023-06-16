package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;

import java.util.List;

public interface TrafficFactorsCalculator {

    ValueWithDateHolder calculateTopMonth(List<TrafficDto> trafficDtos);

    double calculateAverageMonthly(List<TrafficDto> trafficDtos);
}
