package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.function.Function;

public interface AverageFactorCalculator {

    <T> double calculateAverageByDtoList(@NotEmpty List<T> dtos,
                              Function<T, Double> numeratorImpl,
                              Function<T, Double> denominatorImpl);

    double calculateAverage(@NotEmpty List<Double> numerator, @NotEmpty List<Double> denominator);

    double calculateAverage(double numerator, double denominator);
}
