package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToIncorrectDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TopDtoFactorCalculatorImpl<T extends DelayEntityDto> implements TopDtoFactorCalculator<T> {

    @Override
    public ValueWithDateHolder getTopMonthDto(List<T> dtos,
                                              BinaryOperator<T> remappingImpl,
                                              Function<T, Double> averageImpl) {

        if (dtos.isEmpty() || remappingImpl == null || averageImpl == null)
            throw new UnableToCalculateDueToLackOfDataException();

        Map<Integer, T> summedValues = sumDtosInTheSameMonths(dtos, remappingImpl);
        T topMonth = findTopDto(summedValues.values(), averageImpl);

        return createValueHolder(topMonth, averageImpl);
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

    @Override
    public ValueWithDateHolder createValueHolder(T topDto, Function<T, Double> averageImpl) {
        if (topDto == null || averageImpl == null) throw new UnableToCalculateDueToIncorrectDataException();

        LocalDate date = topDto.getDate();
        double value = averageImpl.apply(topDto);

        return new ValueWithDateHolder(date, value);
    }
}
