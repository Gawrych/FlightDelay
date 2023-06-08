package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeService;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AdditionalTimeFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.AdditionalTimeFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorCollectorImpl extends StatisticFactorCollector<AdditionalTimeDto> implements AdditionalTimeFactorCollector {

    private final AdditionalTimeFactorsCalculator additionalTimeFactorsCalculator;

    private final StatisticFactorCreator statisticFactorCreator;

    private final AdditionalTimeService additionalTimeService;

    @Override
    public List<PrecisionFactor> collect(String airportIdent) {
        List<AdditionalTimeDto> additionalTimeDtos = additionalTimeService.findAllLatestByAirport(airportIdent);

        return super.collectFactors(airportIdent, additionalTimeDtos, AdditionalTimeFactor.values());
    }

    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName,
                                              List<AdditionalTimeDto> additionalTimeDtos) {

        List<AdditionalTimeDto> additionalTimeDtosInPhase = additionalTimeDtos.stream()
                .filter(item -> factorName.getPhase().getStage().contains(item.getStage()))
                .toList();

        return switch (factorName.getType()) {
            case TOP_VALUE_WITH_DATE -> statisticFactorCreator.createValueWithDate(
                    factorName,
                    additionalTimeFactorsCalculator.calculateTopDelayMonth(additionalTimeDtosInPhase));

            case AVERAGE -> statisticFactorCreator.createSimpleValue(
                    factorName,
                    additionalTimeFactorsCalculator.calculateAverageFromList(additionalTimeDtosInPhase));

            default -> getNoDataFactor(factorName);
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.createNoDataFactor(factorName);
    }
}