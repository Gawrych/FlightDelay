package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.shared.exception.resource.PreDepartureDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PreDepartureDelayFactorsCalculatorImpl implements PreDepartureDelayFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final TopDtoFactorCalculator<PreDepartureDelayDto> topDtoFactorCalculator;

    private final Function<PreDepartureDelayDto, Double> preDepartureDelayAveraging;

    private final BinaryOperator<PreDepartureDelayDto> preDepartureDelayRemapping;

    @Override
    public double calculateAverageDelayTime(List<PreDepartureDelayDto> preDepartureDtos) {
        if (preDepartureDtos == null || preDepartureDtos.isEmpty()) throw new PreDepartureDelayDataNotFoundException();

        return averageFactorCalculator.calculateAverageByDtoList(
                preDepartureDtos,
                PreDepartureDelayDto::getDelayInMinutes,
                PreDepartureDelayDto::getNumberOfDepartures);
    }

    @Override
    public ValueWithDateHolder calculateTopDayDelay(List<PreDepartureDelayDto> preDepartureDtos) {
        if (preDepartureDtos == null || preDepartureDtos.isEmpty()) throw new PreDepartureDelayDataNotFoundException();

        PreDepartureDelayDto topDelay = topDtoFactorCalculator.findTopDto(
                preDepartureDtos,
                preDepartureDelayAveraging);

        return new ValueWithDateHolder(topDelay.getDate(), preDepartureDelayAveraging.apply(topDelay));
    }

    @Override
    public ValueWithDateHolder calculateTopMonthDelay(List<PreDepartureDelayDto> preDepartureDtos) {
        if (preDepartureDtos == null || preDepartureDtos.isEmpty()) throw new PreDepartureDelayDataNotFoundException();

        PreDepartureDelayDto topMonthDto = topDtoFactorCalculator.getTopMonthDto(
                preDepartureDtos,
                preDepartureDelayRemapping,
                preDepartureDelayAveraging);

        return new ValueWithDateHolder(topMonthDto.getDate(), preDepartureDelayAveraging.apply(topMonthDto));
    }
}
