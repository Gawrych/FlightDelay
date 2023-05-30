package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import java.util.List;

public interface AverageFactorCalculator {

    double calculateAverage(List<Integer> numerator, List<Integer> denominator);

    double calculateAverage(double numerator, double denominator);
}
