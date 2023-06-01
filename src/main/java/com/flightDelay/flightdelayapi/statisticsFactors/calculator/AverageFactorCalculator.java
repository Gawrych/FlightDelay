package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import java.util.List;

public interface AverageFactorCalculator {

    double calculateAverage(List<Double> numerator, List<Double> denominator);

    double calculateAverage(double numerator, double denominator);
}
