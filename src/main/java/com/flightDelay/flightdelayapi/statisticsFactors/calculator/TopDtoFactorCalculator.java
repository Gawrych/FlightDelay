package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public interface TopDtoFactorCalculator<T extends DelayEntityDto> {

    T getTopMonthDto(List<T> dtos, BinaryOperator<T> remappingImpl, Function<T, Double> averageImpl);

    Map<Integer, T> sumDtosInTheSameMonths(List<T> dtos, BinaryOperator<T> remappingImpl);

    T findTopDto(Collection<T> dtos, Function<T, Double> averageImpl);
}
