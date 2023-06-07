package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AverageFactorCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PreDepartureDelayAveragingImpl implements Function<PreDepartureDelayDto, Double> {

    private final AverageFactorCalculator averageFactorCalculator;

    @Override
    public Double apply(PreDepartureDelayDto preDepartureDelayDto) {
        return averageFactorCalculator.calculateAverage(
                preDepartureDelayDto.getDelayInMinutes(),
                preDepartureDelayDto.getNumberOfDepartures());
    }
}
