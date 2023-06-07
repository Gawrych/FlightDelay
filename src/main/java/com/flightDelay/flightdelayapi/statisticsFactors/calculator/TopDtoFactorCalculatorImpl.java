package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopDtoFactorCalculatorImpl<T extends DelayEntityDto> implements TopDtoFactorCalculator<T> {

    @Override
    public ValueWithDateHolder getTopMonthDto(List<T> dtos,
                                              BinaryOperator<T> remappingImpl,
                                              Function<T, Double> averageImpl) {

        Map<Integer, T> summedValues = sumDtosInTheSameMonths(dtos, remappingImpl);
        T topMonth = findTopDto(summedValues.values(), averageImpl);

        return createValueHolder(topMonth, averageImpl);
    }

    @Override
    public Map<Integer, T> sumDtosInTheSameMonths(List<T> dtos, BinaryOperator<T> remappingImpl) {
        Map<Integer, T> mergedValues = new HashMap<>();

        dtos.forEach(element ->
                mergedValues.merge(element.getDate().getMonthValue(), element, remappingImpl));

        return mergedValues;
    }

    @Override
    public T findTopDto(Collection<T> dtos, Function<T, Double> averageImpl) {
        return dtos.stream()
                .max(Comparator.comparing(averageImpl))
                .orElseThrow(UnableToCalculateDueToLackOfDataException::new);
    }

    @Override
    public ValueWithDateHolder createValueHolder(T topDto, Function<T, Double> averageImpl) {
        LocalDate date = topDto.getDate();
        double value = averageImpl.apply(topDto);

        return new ValueWithDateHolder(date, value);
    }
}
