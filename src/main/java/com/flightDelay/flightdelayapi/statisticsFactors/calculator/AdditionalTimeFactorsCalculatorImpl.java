package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorsCalculatorImpl implements AdditionalTimeFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopDtoFactorCalculator<AdditionalTimeDto> topDtoFactorCalculator;

    private final Function<AdditionalTimeDto, Double> additionalTimeAveraging;

    private final BinaryOperator<AdditionalTimeDto> additionalTimeRemapping;

    @Override
    public double calculateAverageFromList(List<AdditionalTimeDto> additionalTimeDtos) {
        return averageFactorCalculator.calculateAverageByDtoList(
                additionalTimeDtos,
                AdditionalTimeDto::getTotalAdditionalTimeInMinutes,
                AdditionalTimeDto::getTotalFlight);
    }

    @Override
    public ValueWithDateHolder calculateTopDelayMonth(List<AdditionalTimeDto> additionalTimeDtos) {
        return topDtoFactorCalculator.getTopMonthDto(
                additionalTimeDtos,
                additionalTimeRemapping,
                additionalTimeAveraging);
    }
}
