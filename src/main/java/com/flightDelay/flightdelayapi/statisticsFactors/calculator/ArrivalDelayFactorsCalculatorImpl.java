package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArrivalDelayFactorsCalculatorImpl implements ArrivalDelayFactorsCalculator {

    @Override
    public ValueWithTextHolder calculateMostCommonDelayCause(List<ArrivalDelayDto> additionalTimes) {
        Map<DelayCause, Integer> delayCause = new HashMap<>();

        for (ArrivalDelayDto dto : additionalTimes) {
            dto.getDelays().forEach((key, value) -> delayCause.merge(key, 1, (oldValue, newValue) -> oldValue + 1));
        }

        Map.Entry<DelayCause, Integer> delayCauseIntegerEntry = delayCause.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(UnableToCalculateDueToLackOfDataException::new);

        List<Map.Entry<DelayCause, Integer>> sortedList = new ArrayList<>(delayCause.entrySet());
        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        System.out.println(sortedList);

        return new ValueWithTextHolder(delayCauseIntegerEntry.getKey().name(), delayCauseIntegerEntry.getValue());
    }
}
