package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayService;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.exception.resource.PreDepartureDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.ArrivalDelayFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.ArrivalDelayFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArrivalDelayFactorCollectorImpl extends StatisticFactorCollector implements ArrivalDelayFactorCollector {

    private final ArrivalDelayService arrivalDelayService;

    private final ArrivalDelayFactorsCalculator arrivalDelayFactorsCalculator;

    private final StatisticFactorCreator statisticFactorCreator;

    @Override
    public List<PrecisionFactor> collect(Flight flight) {
        return super.collectFactors(flight, ArrivalDelayFactor.values());
    }

    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName, String airportIdent)
            throws UnableToCalculateDueToLackOfDataException, PreDepartureDelayDataNotFoundException {

        List<ArrivalDelayDto> additionalTimes = arrivalDelayService.findAllLatestByAirport(airportIdent);

        log.info("{} arrival delay records have been found in the database for airport: {}",
                additionalTimes.size(),
                airportIdent);

        return switch (EnumType.valueOf(ArrivalDelayFactor.class, factorName.name())) {
            case MOST_COMMON_DELAY_CAUSES -> statisticFactorCreator.createListValuesWithText(
                    factorName,
                    arrivalDelayFactorsCalculator.calculateMostCommonDelayCause(additionalTimes));

            case AVERAGE_TIME_TO_PARTICULAR_DELAY_CAUSE -> statisticFactorCreator.createListValuesWithText(
                    factorName,
                    arrivalDelayFactorsCalculator.calculateAverageTimeToParticularDelayCause(additionalTimes));
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.createNoDataFactor(factorName);
    }
}
