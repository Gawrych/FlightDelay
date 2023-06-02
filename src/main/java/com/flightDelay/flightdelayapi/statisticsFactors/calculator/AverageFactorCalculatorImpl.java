package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AverageFactorCalculatorImpl implements AverageFactorCalculator {

    public double calculateAverage(List<Double> numerator, List<Double> denominator) {
        if (numerator.isEmpty() || denominator.isEmpty()) throw new UnableToCalculateDueToLackOfDataException();

        double numeratorSum = numerator.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        double denominatorSum = denominator.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return calculateAverage(numeratorSum, denominatorSum);
    }

    public double calculateAverage(double numerator, double denominator) {
        return Double.isNaN(numerator / denominator)
                ? 0
                : (numerator / denominator);
    }
}
