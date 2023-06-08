package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import jakarta.validation.constraints.NotEmpty;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public interface TopDtoFactorCalculator<T extends DelayEntityDto> {

    ValueWithDateHolder getTopMonthDto(@NotEmpty List<T> dtos,
                                       BinaryOperator<T> remappingImpl,
                                       Function<T, Double> averageImpl);

    Map<Integer, T> sumDtosInTheSameMonths(@NotEmpty List<T> dtos, BinaryOperator<T> remappingImpl);

    T findTopDto(@NotEmpty Collection<T> dtos, Function<T, Double> averageImpl);

    ValueWithDateHolder createValueHolder(T topDto, Function<T, Double> averageImpl);
}
