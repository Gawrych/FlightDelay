package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TopDtoFactorCalculatorImpl<T extends DelayEntityDto> implements TopDtoFactorCalculator<T> {

    @Override
    public T getTopMonthDto(List<T> dtos,
                                              BinaryOperator<T> remappingImpl,
                                              Function<T, Double> averageImpl) {

        if (dtos.isEmpty() || remappingImpl == null || averageImpl == null)
            throw new UnableToCalculateDueToLackOfDataException();

        Map<Integer, T> summedValues = sumDtosInTheSameMonths(dtos, remappingImpl);

        return findTopDto(summedValues.values(), averageImpl);
    }

    @Override
    public Map<Integer, T> sumDtosInTheSameMonths(List<T> dtos, BinaryOperator<T> remappingImpl) {
        if (dtos.isEmpty() || remappingImpl == null) throw new UnableToCalculateDueToLackOfDataException();

        Map<Integer, T> mergedValues = new HashMap<>();

        dtos.forEach(element ->
                mergedValues.merge(element.getDate().getMonthValue(), element, remappingImpl));

        return mergedValues;
    }

    @Override
    public T findTopDto(Collection<T> dtos, Function<T, Double> averageImpl) {
        if (dtos.isEmpty() || averageImpl == null) throw new UnableToCalculateDueToLackOfDataException();

        return dtos.stream()
                .max(Comparator.comparing(averageImpl))
                .orElseThrow(UnableToCalculateDueToLackOfDataException::new);
    }
}
