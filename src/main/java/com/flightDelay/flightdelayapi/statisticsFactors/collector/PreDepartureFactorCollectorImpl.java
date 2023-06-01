package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayService;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.exception.resource.PreDepartureDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.PreDepartureDelayFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.PreDepartureDelayFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreDepartureFactorCollectorImpl extends StatisticFactorCollector implements PreDepartureFactorCollector {

    private final PreDepartureDelayService preDepartureDelayService;

    private final PreDepartureDelayFactorsCalculator preDepartureDelayFactorsCalculator;

    private final StatisticFactorCreator statisticFactorCreator;

    @Override
    public List<PrecisionFactor> getFactors(Flight flight) {
        return super.getFactors(flight, PreDepartureDelayFactor.values());
    }

    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName, String airportIdent)
            throws UnableToCalculateDueToLackOfDataException, PreDepartureDelayDataNotFoundException {

        List<PreDepartureDelayDto> additionalTimes = preDepartureDelayService.findAllLatestByAirport(airportIdent);

        log.info("{} pre departure delay records have been found in the database",
                additionalTimes.size());

        return switch (factorName.getType()) {
            case TOP_VALUE_WITH_DATE -> statisticFactorCreator.createTopValue(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateTopMonthDelay(additionalTimes));

            case TOP_VALUE_WITH_PRECISION_DATE -> statisticFactorCreator.createTopValue(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateTopDayDelay(additionalTimes));

            case AVERAGE -> statisticFactorCreator.createAverage(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateAverageDelayTime(additionalTimes));

            default -> getNoDataFactor(factorName);
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.getNoDataFactor(factorName);
    }
}
