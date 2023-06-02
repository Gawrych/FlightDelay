package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AverageFactorCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreDepartureAverageInstructionImpl implements FactorAverageInstruction<PreDepartureDelayDto> {

    private final AverageFactorCalculator averageFactorCalculator;

    @Override
    public Double getInstruction(PreDepartureDelayDto dto) {
        return averageFactorCalculator.calculateAverage(
                dto.getDelayInMinutes(),
                dto.getNumberOfDepartures());
    }
}
