package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import java.util.List;
import java.util.function.Function;

public interface AverageFactorCalculator {

    <T> double calculateAverageByDtoList(List<T> dtos,
                              Function<T, Double> numeratorImpl,
                              Function<T, Double> denominatorImpl);

    double calculateAverage(List<Double> numerator, List<Double> denominator);

    double calculateAverage(double numerator, double denominator);
}
