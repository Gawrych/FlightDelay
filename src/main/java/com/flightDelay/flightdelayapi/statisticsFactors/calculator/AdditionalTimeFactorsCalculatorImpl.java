package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTime;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.TopMonthValueHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorsCalculatorImpl implements AdditionalTimeFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    @Override
    public TopMonthValueHolder calculateTopMonth(List<AdditionalTime> allByAirportWithDateAfter) {
        if (allByAirportWithDateAfter.isEmpty()) {
            throw new UnableToCalculateDueToLackOfDataException();
        }

        AdditionalTime biggestAdditionalTime = allByAirportWithDateAfter
                .stream()
                .max(Comparator.comparing(
                        e -> averageFactorCalculator.calculateAverage(
                                e.getTotalAdditionalTimeInMinutes(),
                                e.getTotalFlight())))
                .get();

        return new TopMonthValueHolder(
                Month.of(biggestAdditionalTime.getMonthNum()).name(),
                averageFactorCalculator.calculateAverage(
                        biggestAdditionalTime.getTotalAdditionalTimeInMinutes(),
                        biggestAdditionalTime.getTotalFlight()));
    }

    @Override
    public double calculateAverageFromList(List<AdditionalTime> allByAirportWithDateAfter) {
        if (allByAirportWithDateAfter.isEmpty()) {
            throw new UnableToCalculateDueToLackOfDataException();
        }

        List<Integer> numerator = allByAirportWithDateAfter.stream().map(AdditionalTime::getTotalAdditionalTimeInMinutes).toList();
        List<Integer> denominator = allByAirportWithDateAfter.stream().map(AdditionalTime::getTotalFlight).toList();

        return averageFactorCalculator.calculateAverage(numerator, denominator);
    }
}
