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
        String airportIdent = flight.getAirportIdent();

        for (AdditionalTimeFactors factorName : AdditionalTimeFactors.values()) {
            try {
                PrecisionFactor factor = switch (factorName) {
                    case TOP_MONTH_ARRIVAL -> getTopMonth(factorName, airportIdent, FlightPhase.ARRIVAL);
                    case TOP_MONTH_DEPARTURE -> getTopMonth(factorName, airportIdent, FlightPhase.DEPARTURE);
                    case AVERAGE_ARRIVAL -> getAverage(factorName, airportIdent, FlightPhase.ARRIVAL);
                    case AVERAGE_DEPARTURE -> getAverage(factorName, airportIdent, FlightPhase.DEPARTURE);
                };

                statisticsData.add(factorName, factor);

            } catch (UnableToCalculateDueToLackOfDataException e) {
                log.warn("Unable to calculate due to lack of data for airport with ident: {}", airportIdent);

                statisticsData.add(factorName, getNoDataFactor(factorName));
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
                additionalTimeFactorsCalculator.calculateTopMonth(allByAirportWithDateAfter));
    }

    private PrecisionFactor getAverage(StatisticFactorName factorName, String airportIdent, FlightPhase phase) {
        List<AdditionalTime> allByAirportWithDateAfter = additionalTimeService
                .findAllLatestAdditionalTimeByAirport(airportIdent, phase);

        log.debug("{} additional time records have been found in database", allByAirportWithDateAfter.size());

        return statisticFactorCreator.createAverage(
                factorName,
                additionalTimeFactorsCalculator.calculateAverageFromList(allByAirportWithDateAfter));
    }

    private PrecisionFactor getNoDataFactor(StatisticFactorName factorName) {
        return statisticFactorCreator.getNoDataFactor(factorName);
    }
}
