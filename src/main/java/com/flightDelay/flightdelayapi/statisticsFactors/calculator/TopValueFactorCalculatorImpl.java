package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
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

    public Map<Integer, T> sumValuesInTheSameMonth(List<T> dtos, RemappingInstruction<T> process) {
        Map<Integer, T> mergedValues = new HashMap<>();

        dtos.forEach(element ->
                mergedValues.merge(element.getDate().getMonthValue(), element, process::getInstruction));

        return mergedValues;
    }

    public T findTopValue(Collection<T> dtos, FactorAverageCalculatorInstruction<T> instruction) {
        return dtos.stream()
                .max(Comparator.comparing(instruction::getInstruction))
                .orElseThrow(UnableToCalculateDueToLackOfDataException::new);
    }

    public ValueWithDateHolder createValueHolder(T topDto, FactorAverageCalculatorInstruction<T> instruction) {
        LocalDate date = topDto.getDate();
        double value = instruction.getInstruction(topDto);

        return new ValueWithDateHolder(date, value);
    }
}
