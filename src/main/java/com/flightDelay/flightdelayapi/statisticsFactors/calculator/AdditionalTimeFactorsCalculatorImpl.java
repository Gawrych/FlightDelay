package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.AdditionalTimeDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorsCalculatorImpl implements AdditionalTimeFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopDtoFactorCalculator<AdditionalTimeDto> topDtoFactorCalculator;

    private final Function<AdditionalTimeDto, Double> additionalTimeAveraging;

    private final BinaryOperator<AdditionalTimeDto> additionalTimeRemapping;

    @Override
    public double calculateAverageFromList(List<AdditionalTimeDto> additionalTimeDtos) {
        if (additionalTimeDtos == null || additionalTimeDtos.isEmpty()) throw new AdditionalTimeDataNotFoundException();

        return averageFactorCalculator.calculateAverageByDtoList(
                additionalTimeDtos,
                AdditionalTimeDto::getTotalAdditionalTimeInMinutes,
                AdditionalTimeDto::getTotalFlight);
    }

    @Override
    public ValueWithDateHolder calculateTopDelayMonth(List<AdditionalTimeDto> additionalTimeDtos) {
        if (additionalTimeDtos == null || additionalTimeDtos.isEmpty()) throw new AdditionalTimeDataNotFoundException();

        return topDtoFactorCalculator.getTopMonthDto(
                additionalTimeDtos,
                additionalTimeRemapping,
                additionalTimeAveraging);
    }
}
