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
import jakarta.persistence.EnumType;
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
    public List<PrecisionFactor> collect(Flight flight) {
        return super.collectFactors(flight, PreDepartureDelayFactor.values());
    }

    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName, String airportIdent)
            throws UnableToCalculateDueToLackOfDataException, PreDepartureDelayDataNotFoundException {

        List<PreDepartureDelayDto> additionalTimes = preDepartureDelayService.findAllLatestByAirport(airportIdent);

        log.info("{} pre departure delay records have been found in the database for airport: {}",
                additionalTimes.size(),
                airportIdent);


        return switch (EnumType.valueOf(PreDepartureDelayFactor.class, factorName.name())) {
            case TOP_MONTH_OF_PRE_DEPARTURE_DELAY -> statisticFactorCreator.createValueWithDate(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateTopMonthDelay(additionalTimes));

            case TOP_DAY_OF_PRE_DEPARTURE_DELAY -> statisticFactorCreator.createValueWithDate(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateTopDayDelay(additionalTimes));

            case AVERAGE_PRE_DEPARTURE_DELAY -> statisticFactorCreator.createSimpleValue(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateAverageDelayTime(additionalTimes));
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.createNoDataFactor(factorName);
    }
}
