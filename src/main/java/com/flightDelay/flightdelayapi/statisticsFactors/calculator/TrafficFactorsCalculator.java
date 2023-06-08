package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public interface TrafficFactorsCalculator {

    ValueWithDateHolder calculateTopMonthTraffic(@NotEmpty List<TrafficDto> trafficDtos);

    double calculateAverageMonthlyTraffic(@NotEmpty List<TrafficDto> trafficDtos);
}
