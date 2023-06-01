package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDtoMapper;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorsCalculatorImpl implements AdditionalTimeFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopValueFactorCalculator<AdditionalTimeDto> topValueFactorCalculator;

    private final AdditionalTimeDtoMapper mapper;

    @Override
    public double calculateAverageFromList(List<AdditionalTimeDto> additionalTimeDtos) {
        List<Double> numerator = additionalTimeDtos.stream()
                .map(AdditionalTimeDto::getTotalAdditionalTimeInMinutes)
                .toList();

        List<Double> denominator = additionalTimeDtos.stream()
                .map(AdditionalTimeDto::getTotalFlight)
                .toList();

        return averageFactorCalculator.calculateAverage(numerator, denominator);
    }

    @Override
    public ValueWithDateHolder calculateTopDelayMonth(List<AdditionalTimeDto> additionalTimeDtos) {
        FactorAverageCalculatorInstruction<AdditionalTimeDto> averageInstruction = getAverageInstruction();

        Map<Integer, AdditionalTimeDto> summedValues = topValueFactorCalculator.sumValuesInTheSameMonth(
                additionalTimeDtos,
                getRemappingInstruction());

        AdditionalTimeDto topMonth = topValueFactorCalculator.findTopValue(
                summedValues.values(),
                averageInstruction);

       return topValueFactorCalculator.createValueHolder(topMonth, averageInstruction);
    }

    private FactorAverageCalculatorInstruction<AdditionalTimeDto> getAverageInstruction() {
        return dto -> averageFactorCalculator.calculateAverage(
                dto.getTotalAdditionalTimeInMinutes(),
                dto.getTotalFlight());
    }

    private RemappingInstruction<AdditionalTimeDto> getRemappingInstruction() {
        return (oldRecord, newRecord) -> {
            double mergedFlights = (oldRecord.getTotalFlight() + newRecord.getTotalFlight()) / 2.0;
            double mergedAdditionalTime = oldRecord.getTotalAdditionalTimeInMinutes() + newRecord.getTotalAdditionalTimeInMinutes();

            return mapper.mapFrom(newRecord.getDate(), mergedFlights, mergedAdditionalTime);
        };
    }
}
