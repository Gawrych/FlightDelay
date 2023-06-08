package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Slf4j
@Component
@Validated
@RequiredArgsConstructor
public class AdditionalTimeFactorsCalculatorImpl implements AdditionalTimeFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopDtoFactorCalculator<AdditionalTimeDto> topDtoFactorCalculator;

    private final Function<AdditionalTimeDto, Double> additionalTimeAveraging;

    private final BinaryOperator<AdditionalTimeDto> additionalTimeRemapping;

    @Override
    public double calculateAverageFromList(@NotEmpty List<AdditionalTimeDto> additionalTimeDtos) {
        return averageFactorCalculator.calculateAverageByDtoList(
                additionalTimeDtos,
                AdditionalTimeDto::getTotalAdditionalTimeInMinutes,
                AdditionalTimeDto::getTotalFlight);
    }

    @Override
    public ValueWithDateHolder calculateTopDelayMonth(@NotEmpty List<AdditionalTimeDto> additionalTimeDtos) {
        return topDtoFactorCalculator.getTopMonthDto(
                additionalTimeDtos,
                additionalTimeRemapping,
                additionalTimeAveraging);
    }
}
