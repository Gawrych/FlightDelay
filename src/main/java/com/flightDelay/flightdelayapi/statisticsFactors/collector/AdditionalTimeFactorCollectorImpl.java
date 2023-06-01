package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeService;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.exception.resource.ResourceNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AdditionalTimeFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.AdditionalTimeFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorCollectorImpl extends StatisticFactorCollector implements AdditionalTimeFactorCollector {

    private final AdditionalTimeFactorsCalculator additionalTimeFactorsCalculator;

    private final StatisticFactorCreator statisticFactorCreator;

    private final AdditionalTimeService additionalTimeService;

    @Override
    public List<PrecisionFactor> getFactors(Flight flight) {
        return super.getFactors(flight, AdditionalTimeFactor.values());
    }

    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName, String airportIdent)
            throws UnableToCalculateDueToLackOfDataException, ResourceNotFoundException {

        List<AdditionalTimeDto> additionalTimes = additionalTimeService
                .findAllLatestByAirport(airportIdent, factorName.getPhase());

        log.info("{} additional time records have been found in the database",
                additionalTimes.size());

        return switch (factorName.getType()) {
            case TOP_VALUE_WITH_DATE -> statisticFactorCreator.createTopValue(
                    factorName,
                    additionalTimeFactorsCalculator.calculateTopDelayMonth(additionalTimes));

            case AVERAGE -> statisticFactorCreator.createAverage(
                    factorName,
                    additionalTimeFactorsCalculator.calculateAverageFromList(additionalTimes));

            default -> getNoDataFactor(factorName);
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.getNoDataFactor(factorName);
    }
}