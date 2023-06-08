package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
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
@Validated
@Component
@RequiredArgsConstructor
public class PreDepartureDelayFactorsCalculatorImpl implements PreDepartureDelayFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopDtoFactorCalculator<PreDepartureDelayDto> topDtoFactorCalculator;

    private final Function<PreDepartureDelayDto, Double> preDepartureDelayAveraging;

    private final BinaryOperator<PreDepartureDelayDto> preDepartureDelayRemapping;

    @Override
    public double calculateAverageDelayTime(@NotEmpty List<PreDepartureDelayDto> preDepartureDelayDtos) {
        return averageFactorCalculator.calculateAverageByDtoList(
                preDepartureDelayDtos,
                PreDepartureDelayDto::getDelayInMinutes,
                PreDepartureDelayDto::getNumberOfDepartures);
    }

    @Override
    public ValueWithDateHolder calculateTopDayDelay(@NotEmpty List<PreDepartureDelayDto> preDepartureDelayDtos) {
        PreDepartureDelayDto topDelay = topDtoFactorCalculator.findTopDto(
                preDepartureDelayDtos,
                preDepartureDelayAveraging);

        return topDtoFactorCalculator.createValueHolder(topDelay, preDepartureDelayAveraging);
    }

    @Override
    public ValueWithDateHolder calculateTopMonthDelay(@NotEmpty List<PreDepartureDelayDto> preDepartureDelayDtos) {
        return topDtoFactorCalculator.getTopMonthDto(
                preDepartureDelayDtos,
                preDepartureDelayRemapping,
                preDepartureDelayAveraging);
    }
}
