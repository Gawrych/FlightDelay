package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.exception.resource.TrafficDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Slf4j
@Component
@ToString
@Validated
@RequiredArgsConstructor
public class TrafficFactorsCalculatorImpl implements TrafficFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopDtoFactorCalculator<TrafficDto> topDtoFactorCalculator;

    private final Function<TrafficDto, Double> trafficAveraging;

    private final BinaryOperator<TrafficDto> trafficRemapping;

    @Override
    public ValueWithDateHolder calculateTopMonthTraffic(List<TrafficDto> trafficDtos) {
        if (trafficDtos == null || trafficDtos.isEmpty()) throw new TrafficDataNotFoundException();

        return topDtoFactorCalculator.getTopMonthDto(trafficDtos, trafficRemapping, trafficAveraging);
    }

    @Override
    public double calculateAverageMonthlyTraffic(List<TrafficDto> trafficDtos) {
        if (trafficDtos == null ||trafficDtos.isEmpty()) throw new TrafficDataNotFoundException();

        Map<Integer, TrafficDto> mergedValues = topDtoFactorCalculator.sumDtosInTheSameMonths(
                trafficDtos,
                trafficRemapping);

        int sumOfAllTraffic = mergedValues.values()
                .stream()
                .mapToInt(TrafficDto::getTotal)
                .sum();

        double amountOfMonths = mergedValues.keySet().size();

        return averageFactorCalculator.calculateAverage(sumOfAllTraffic, amountOfMonths);
    }
}
