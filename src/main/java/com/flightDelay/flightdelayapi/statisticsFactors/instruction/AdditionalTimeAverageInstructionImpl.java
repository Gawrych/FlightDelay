package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AverageFactorCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdditionalTimeAverageInstructionImpl implements FactorAverageInstruction<AdditionalTimeDto> {

    private final AverageFactorCalculator averageFactorCalculator;

    @Override
    public Double getInstruction(AdditionalTimeDto dto) {
        return averageFactorCalculator.calculateAverage(
                dto.getTotalAdditionalTimeInMinutes(),
                dto.getTotalFlight());
    }
}
