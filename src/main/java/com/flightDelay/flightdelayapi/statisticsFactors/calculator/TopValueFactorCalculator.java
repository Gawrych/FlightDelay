package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.instruction.FactorAverageInstruction;
import com.flightDelay.flightdelayapi.statisticsFactors.instruction.RemappingInstruction;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface TopValueFactorCalculator<T extends DelayEntityDto> {

    Map<Integer, T> sumValuesInTheSameMonth(List<T> dtos, RemappingInstruction<T> process);

    T findTopValue(Collection<T> dtos, FactorAverageInstruction<T> instruction);

    ValueWithDateHolder createValueHolder(T topDto, FactorAverageInstruction<T> instruction);
}
