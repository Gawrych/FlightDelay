package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AverageFactorCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AdditionalTimeAveragingImpl implements Function<AdditionalTimeDto, Double> {

    private final AverageFactorCalculator averageFactorCalculator;

    @Override
    public Double apply(AdditionalTimeDto additionalTimeDto) {
        return averageFactorCalculator.calculateAverage(
                additionalTimeDto.getTotalAdditionalTimeInMinutes(),
                additionalTimeDto.getTotalFlight());
    }
}
