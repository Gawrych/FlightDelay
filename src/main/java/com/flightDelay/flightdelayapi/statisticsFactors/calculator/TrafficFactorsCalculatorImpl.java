package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrafficFactorsCalculatorImpl implements TrafficFactorsCalculator {

    @Override
    public double calculateAverageMonthlyAmountOfDepartures(List<TrafficDto> trafficDtos) {
//        Map<Integer, Integer> departuresTraffic = trafficDtos.stream()
//                .collect(Collectors.toMap(
//                        dto -> dto.getDate().getMonthValue(),
//                        TrafficDto::getDepartures));
//
//        int sumOfAllDepartures = departuresTraffic.values().stream().mapToInt(Integer::intValue).sum();
//        double amountOfMonths = departuresTraffic.keySet().size();

        int sumOfAllDepartures = trafficDtos.stream().mapToInt(TrafficDto::getDepartures).sum();
        double amountOfMonths = trafficDtos.size();

        return sumOfAllDepartures / amountOfMonths;
    }

    @Override
    public double calculateAverageMonthlyAmountOfArrival(List<TrafficDto> trafficDtos) {
        int sumOfAllArrivals = trafficDtos.stream().mapToInt(TrafficDto::getArrivals).sum();
        double amountOfMonths = trafficDtos.size();

        return sumOfAllArrivals / amountOfMonths;
    }
}
