package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToIncorrectDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class AverageFactorCalculatorImpl implements AverageFactorCalculator {

    public <T> double  calculateAverageByDtoList(List<T> dtos,
                                                 Function<T, Double> numeratorImpl,
                                                 Function<T, Double> denominatorImpl) {

        if (dtos.isEmpty()) throw new UnableToCalculateDueToLackOfDataException();

        List<Double> numerator = dtos.stream()
                .map(numeratorImpl)
                .toList();

        List<Double> denominator = dtos.stream()
                .map(denominatorImpl)
                .toList();

        return calculateAverage(numerator, denominator);
    }

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
        if (denominator == 0) {
            log.warn("Dividing by zero");
            throw new UnableToCalculateDueToIncorrectDataException();
        }

        return numerator / denominator;
    }
}
