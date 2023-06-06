package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.traffic.TrafficDto;

import java.util.List;

public interface TrafficFactorsCalculator {

    double calculateAverageMonthlyAmountOfDepartures(List<TrafficDto> trafficDtos);

    double calculateAverageMonthlyAmountOfArrival(List<TrafficDto> trafficDtos);

//    double calculateAverageMonthlyTraffic(List<TrafficDto> trafficDtos);
//
//    ValueWithDateHolder calculateTopMonthTraffic(List<TrafficDto> trafficDtos);
}
