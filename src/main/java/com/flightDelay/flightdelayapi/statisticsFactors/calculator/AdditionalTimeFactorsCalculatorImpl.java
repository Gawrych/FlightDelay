package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTime;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDtoMapper;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.TopMonthValueHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorsCalculatorImpl implements AdditionalTimeFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final AdditionalTimeDtoMapper additionalTimeDtoMapper;

    @Override
    public TopMonthValueHolder calculateTopMonth(List<AdditionalTime> allByAirportWithDateAfter) {
        Map<LocalDate, AdditionalTimeDto> summedValues = sumValuesUnderTheSameDate(allByAirportWithDateAfter);

        AdditionalTimeDto topMonth = findTopMonth(summedValues);

        Month month = topMonth.getDate().getMonth();
        int monthNum = month.getValue();
        String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, LocaleContextHolder.getLocale());

        double value = averageFactorCalculator.calculateAverage(
                topMonth.getTotalAdditionalTimeInMinutes(),
                topMonth.getTotalFlight());

        return new TopMonthValueHolder(monthName, monthNum, value);
    }

    @Override
    public double calculateAverageFromList(List<AdditionalTime> allByAirportWithDateAfter) {
        List<Integer> numerator = allByAirportWithDateAfter.stream()
                .map(AdditionalTime::getTotalAdditionalTimeInMinutes)
                .toList();

        List<Integer> denominator = allByAirportWithDateAfter.stream()
                .map(AdditionalTime::getTotalFlight)
                .toList();

        return averageFactorCalculator.calculateAverage(numerator, denominator);
    }

    private AdditionalTimeDto findTopMonth(Map<LocalDate, AdditionalTimeDto> summedVariables) {
        return summedVariables.values()
                .stream()
                .max(Comparator.comparing(
                        dto -> averageFactorCalculator.calculateAverage(
                                dto.getTotalAdditionalTimeInMinutes(),
                                dto.getTotalFlight())))
                .orElseThrow(UnableToCalculateDueToLackOfDataException::new);
    }

    private Map<LocalDate, AdditionalTimeDto> sumValuesUnderTheSameDate(List<AdditionalTime> allByAirportWithDateAfter) {
        Map<LocalDate, AdditionalTimeDto> mergedValues = new HashMap<>();

        allByAirportWithDateAfter.forEach(element -> {
            AdditionalTimeDto elementDto = additionalTimeDtoMapper.mapFrom(element);
            mergedValues.merge(element.getFlightDate(), elementDto, this::mergeAdditionalTimeDto);
        });

        return mergedValues;
    }

    private AdditionalTimeDto mergeAdditionalTimeDto(AdditionalTimeDto oldRecord, AdditionalTimeDto newRecord) {
        double mergedFlights = (oldRecord.getTotalFlight() + newRecord.getTotalFlight()) / 2.0;
        double mergedAdditionalTime = (oldRecord.getTotalAdditionalTimeInMinutes() + newRecord.getTotalAdditionalTimeInMinutes());

        return additionalTimeDtoMapper.mapFrom(newRecord.getDate(), mergedFlights, mergedAdditionalTime);
    }
}
