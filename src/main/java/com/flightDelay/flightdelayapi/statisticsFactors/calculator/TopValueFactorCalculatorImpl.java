package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.instruction.FactorAverageInstruction;
import com.flightDelay.flightdelayapi.statisticsFactors.instruction.RemappingInstruction;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopValueFactorCalculatorImpl<T extends DelayEntityDto> implements TopValueFactorCalculator<T> {

    public Map<Integer, T> sumValuesInTheSameMonth(List<T> dtos, RemappingInstruction<T> remappingInstruction) {
        Map<Integer, T> mergedValues = new HashMap<>();

        dtos.forEach(element ->
                mergedValues.merge(element.getDate().getMonthValue(), element, remappingInstruction::getInstruction));

        return mergedValues;
    }

    public T findTopValue(Collection<T> dtos, FactorAverageInstruction<T> averageInstruction) {
        return dtos.stream()
                .max(Comparator.comparing(averageInstruction::getInstruction))
                .orElseThrow(UnableToCalculateDueToLackOfDataException::new);
    }

    public ValueWithDateHolder createValueHolder(T topDto, FactorAverageInstruction<T> averageInstruction) {
        LocalDate date = topDto.getDate();
        double value = averageInstruction.getInstruction(topDto);

        return new ValueWithDateHolder(date, value);
    }
}
