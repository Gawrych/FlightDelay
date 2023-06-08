package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToIncorrectDataException;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Validated
@Component
public class AverageFactorCalculatorImpl implements AverageFactorCalculator {

    public <T> double  calculateAverageByDtoList(@NotEmpty List<T> dtos,
                                                 Function<T, Double> numeratorImpl,
                                                 Function<T, Double> denominatorImpl) {

        List<Double> numerator = dtos.stream()
                .map(numeratorImpl)
                .toList();

        List<Double> denominator = dtos.stream()
                .map(denominatorImpl)
                .toList();

        return calculateAverage(numerator, denominator);
    }

    public double calculateAverage(@NotEmpty List<Double> numerator, @NotEmpty List<Double> denominator) {
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
