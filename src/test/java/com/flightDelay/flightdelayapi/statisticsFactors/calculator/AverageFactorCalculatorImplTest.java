package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AverageFactorCalculatorImplTest {

    @Autowired
    private AverageFactorCalculatorImpl averageFactorCalculator;

    @Test
    void calculateAverage() {
        List<Double> numerator = List.of(1.0, 2.0, 3.0); // 6
        List<Double> denominator = List.of(1.0, 2.0); // 3
        double average = averageFactorCalculator.calculateAverage(numerator, denominator);
        assertEquals(2.0, average);
    }
}