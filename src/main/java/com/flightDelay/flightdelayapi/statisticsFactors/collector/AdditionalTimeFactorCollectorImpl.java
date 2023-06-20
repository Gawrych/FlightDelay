package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeService;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AdditionalTimeFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.AdditionalTimeFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorCollectorImpl extends StatisticFactorCollector<AdditionalTimeDto> implements AdditionalTimeFactorCollector {

    private final AdditionalTimeFactorsCalculator additionalTimeFactorsCalculator;

    private final StatisticReportCreator statisticReportCreator;

    private final AdditionalTimeService additionalTimeService;

    @Override
    public List<PrecisionReport> collect(String airportCode) {
        List<AdditionalTimeDto> additionalTimeDtos = additionalTimeService.findAllLatestByAirport(airportCode);

        return super.collectFactors(airportCode, additionalTimeDtos, AdditionalTimeFactor.values());
    }

    @Override
    protected PrecisionReport calculateFactor(EntityStatisticFactor factorName,
                                              List<AdditionalTimeDto> additionalTimeDtos) {

        List<AdditionalTimeDto> additionalTimeDtosInPhase = additionalTimeDtos.stream()
                .filter(item -> factorName.getPhase().getStage().contains(item.getStage()))
                .toList();

        return switch (factorName.getType()) {
            case TOP_VALUE_WITH_DATE -> {
                ValueWithDateHolder calculatedValue = additionalTimeFactorsCalculator
                        .calculateTopMonthDelay(additionalTimeDtosInPhase);

                yield statisticReportCreator.create(factorName, calculatedValue);
            }

            case AVERAGE -> {
                double calculatedValue = additionalTimeFactorsCalculator
                        .calculateAverageFromList(additionalTimeDtosInPhase);

                yield statisticReportCreator.create(factorName, calculatedValue);
            }

            default -> getNoDataFactor(factorName);
        };
    }

    @Override
    protected PrecisionReport getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticReportCreator.create(factorName);
    }
}