package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTime;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeService;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AdditionalTimeFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.AdditionalTimeFactors;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorName;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.StatisticsData;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdditionalTimeFactorCollectorImpl implements AdditionalTimeFactorCollector {

    private final AdditionalTimeFactorsCalculator additionalTimeFactorsCalculator;

    private final StatisticFactorCreator statisticFactorCreator;

    private final AdditionalTimeService additionalTimeService;

    public StatisticsData getFactors(Flight flight) {
        StatisticsData statisticsData = new StatisticsData();
        FlightPhase phase = flight.getPhase();
        String airportIdent = flight.getAirportIdent();

        for (AdditionalTimeFactors factorName : AdditionalTimeFactors.values()) {
            try {
                PrecisionFactor factor = switch (factorName) {
                    case TOP_MONTH -> getTopMonth(factorName, airportIdent, phase);
                    case AVERAGE -> getAverage(factorName, airportIdent, phase);
                };

                statisticsData.add(factorName, factor);

            } catch (UnableToCalculateDueToLackOfDataException e) {
                log.warn("Unable to calculate due to lack of data for airport with ident: {}", airportIdent);

                statisticsData.add(factorName, getNoDataFactor(factorName, phase));
            }
        }

        return statisticsData;
    }

    private PrecisionFactor getTopMonth(StatisticFactorName factorName, String airportIdent, FlightPhase phase) {
        List<AdditionalTime> allByAirportWithDateAfter = additionalTimeService
                .findAllLatestAdditionalTimeByAirport(airportIdent, phase);

        log.debug("{} additional time records have been found in database", allByAirportWithDateAfter.size());

        return statisticFactorCreator.createTopMonth(
                factorName,
                phase,
                additionalTimeFactorsCalculator.calculateTopMonth(allByAirportWithDateAfter));
    }

    private PrecisionFactor getAverage(StatisticFactorName factorName, String airportIdent, FlightPhase phase) {
        List<AdditionalTime> allByAirportWithDateAfter = additionalTimeService
                .findAllLatestAdditionalTimeByAirport(airportIdent, phase);

        log.debug("{} additional time records have been found in database", allByAirportWithDateAfter.size());

        return statisticFactorCreator.createAverage(
                factorName,
                phase,
                additionalTimeFactorsCalculator.calculateAverageFromList(allByAirportWithDateAfter));
    }

    private PrecisionFactor getNoDataFactor(StatisticFactorName factorName, FlightPhase phase) {
        return statisticFactorCreator.getNoDataFactor(factorName, phase);
    }
}
