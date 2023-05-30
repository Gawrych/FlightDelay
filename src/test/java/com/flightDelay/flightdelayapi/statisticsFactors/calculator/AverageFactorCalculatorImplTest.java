package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AverageFactorCalculatorImplTest {

    @Autowired
    private AverageFactorCalculatorImpl averageFactorCalculator;

    @Test
    void calculateAverage() {
        List<Integer> numerator = List.of(1, 2, 3); // 6
        List<Integer> denominator = List.of(1, 2); // 3
        double average = averageFactorCalculator.calculateAverage(numerator, denominator);
        assertEquals(2.0, average);
    }
}