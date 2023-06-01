package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface TopValueFactorCalculator<T extends DelayEntityDto> {

    Map<Integer, T> sumValuesInTheSameMonth(List<T> dtos, RemappingInstruction<T> process);

    T findTopValue(Collection<T> dtos, FactorAverageCalculatorInstruction<T> instruction);

    ValueWithDateHolder createValueHolder(T topDto, FactorAverageCalculatorInstruction<T> instruction);
}
