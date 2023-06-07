package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BinaryOperator;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArrivalDelayFactorsCalculatorImpl implements ArrivalDelayFactorsCalculator {

    @Value("${statistics.itemsLimitOnListsFactors}")
    private int listLimit;

    @Override
    public List<ValueWithTextHolder> calculateMostCommonDelayCause(List<ArrivalDelayDto> additionalTimes) {
        Map<DelayCause, Integer> delayCause = calculateFrequencyOfEachDelayCause(additionalTimes);
        List<ValueWithTextHolder> result = createSortedValueWithTextHolderList(delayCause);

        return limitList(result);
    }

    @Override
    public List<ValueWithTextHolder> calculateAverageTimeToParticularDelayCause(List<ArrivalDelayDto> additionalTimes) {
        Map<DelayCause, Integer> delayCauseWithDelayTimeInMinutes = calculateDelayTimeInMinutes(additionalTimes);
        Map<DelayCause, Integer> delayCause = calculateTotalNumberOfDelayedArrivals(additionalTimes);

        mergeDelayCauseMaps(delayCauseWithDelayTimeInMinutes, delayCause);

        List<ValueWithTextHolder> result = createSortedValueWithTextHolderList(delayCause);

        return limitList(result);
    }

    private List<ValueWithTextHolder> createSortedValueWithTextHolderList(Map<DelayCause, Integer> delayCause) {
        List<Map.Entry<DelayCause, Integer>> sortedList = new ArrayList<>(delayCause.entrySet());

        sortedList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        return sortedList.stream()
                .map(entry -> new ValueWithTextHolder(entry.getKey().name(), entry.getValue()))
                .toList();
    }

    private List<ValueWithTextHolder> limitList(List<ValueWithTextHolder> list) {
        return list.stream()
                .limit(listLimit)
                .toList();
    }

    private void mergeDelayCauseMaps(Map<DelayCause, Integer> delayCauseWithDelayTimeInMinutes,
                                     Map<DelayCause, Integer> delayCause) {
        delayCauseWithDelayTimeInMinutes.forEach((key, value) -> delayCause.merge(key, value, this::calculateAverage));
    }

    private Map<DelayCause, Integer> calculateFrequencyOfEachDelayCause(List<ArrivalDelayDto> additionalTimes) {
        Map<DelayCause, Integer> delayCause = new HashMap<>();

        additionalTimes.forEach(
                dto -> dto
                        .getDelays()
                        .forEach((key, value) -> delayCause.merge(key, 1, this::iterate)));

        return delayCause;
    }

    private Map<DelayCause, Integer> calculateDelayTimeInMinutes(List<ArrivalDelayDto> additionalTimes) {
        Map<DelayCause, Integer> delayCauseWithDelayTimeInMinutes = new HashMap<>();

        additionalTimes.forEach(
                dto -> dto.
                        getDelays()
                        .forEach((key, value) -> delayCauseWithDelayTimeInMinutes.merge(key, value, Integer::sum)));

        return delayCauseWithDelayTimeInMinutes;
    }

    private Map<DelayCause, Integer> calculateTotalNumberOfDelayedArrivals(List<ArrivalDelayDto> additionalTimes) {
        Map<DelayCause, Integer> delayCause = new HashMap<>();

        additionalTimes.forEach(
                dto -> dto
                        .getDelays()
                        .forEach((key, value) -> delayCause.merge(key, dto.getNumberOfDelayedArrivals(), sum(dto))));

        return delayCause;
    }

    private BinaryOperator<Integer> sum(ArrivalDelayDto dto) {
        return (oldValue, newValue) -> oldValue + dto.getNumberOfDelayedArrivals();
    }

    private int iterate(int oldValue, int newValue) {
        return oldValue + 1;
    }

    private Integer calculateAverage(Integer oldValue, Integer newValue) {
        return newValue / oldValue;
    }
}