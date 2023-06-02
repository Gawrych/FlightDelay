package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.instruction.FactorAverageInstruction;
import com.flightDelay.flightdelayapi.statisticsFactors.instruction.RemappingInstruction;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreDepartureDelayFactorsCalculatorImpl implements PreDepartureDelayFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopValueFactorCalculator<PreDepartureDelayDto> topValueFactorCalculator;

    private final FactorAverageInstruction<PreDepartureDelayDto> averageInstruction;

    private final RemappingInstruction<PreDepartureDelayDto> remappingInstruction;

    @Override
    public double calculateAverageDelayTime(List<PreDepartureDelayDto> preDepartureDelayDtos) {
        List<Double> numerator = preDepartureDelayDtos.stream()
                .map(PreDepartureDelayDto::getDelayInMinutes)
                .toList();

        List<Double> denominator = preDepartureDelayDtos.stream()
                .map(PreDepartureDelayDto::getNumberOfDepartures)
                .toList();

        return averageFactorCalculator.calculateAverage(numerator, denominator);
    }

    @Override
    public ValueWithDateHolder calculateTopDayDelay(List<PreDepartureDelayDto> preDepartureDelayDtos) {
        PreDepartureDelayDto topDelay = topValueFactorCalculator.findTopValue(
                preDepartureDelayDtos,
                averageInstruction);

        return topValueFactorCalculator.createValueHolder(topDelay, averageInstruction);
    }

    @Override
    public ValueWithDateHolder calculateTopMonthDelay(List<PreDepartureDelayDto> preDepartureDelayDtos) {
        Map<Integer, PreDepartureDelayDto> summedValues = topValueFactorCalculator.sumValuesInTheSameMonth(
                preDepartureDelayDtos,
                remappingInstruction);

        PreDepartureDelayDto topMonth = topValueFactorCalculator.findTopValue(
                summedValues.values(),
                averageInstruction);

        return topValueFactorCalculator.createValueHolder(topMonth, averageInstruction);
    }
}
