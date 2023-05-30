package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTime;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeService;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.exception.resource.ResourceNotFoundException;
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
                PrecisionFactor factor = calculateFactor(factorName, airportIdent, phase);
                statisticsData.add(factorName, factor);

            } catch (UnableToCalculateDueToLackOfDataException | ResourceNotFoundException e) {
                log.warn("Unable to calculate {} due to lack of data for airport with ident: {}",
                        factorName,
                        airportIdent);

                PrecisionFactor factor = getNoDataFactor(factorName, phase);
                statisticsData.add(factorName, factor);
            }
        }

        return statisticsData;
    }

    private PrecisionFactor calculateFactor(AdditionalTimeFactors factorName, String airportIdent, FlightPhase phase)
            throws UnableToCalculateDueToLackOfDataException, ResourceNotFoundException {

        List<AdditionalTime> additionalTimes = additionalTimeService
                .findAllLatestAdditionalTimeByAirport(airportIdent, phase);

        log.info("{} additional time records have been found in the database for factor: {}",
                additionalTimes.size(),
                factorName);

        return switch (factorName) {
            case TOP_MONTH -> statisticFactorCreator.createTopMonth(
                    factorName,
                    phase,
                    additionalTimeFactorsCalculator.calculateTopMonth(additionalTimes));

            case AVERAGE -> statisticFactorCreator.createAverage(
                    factorName,
                    phase,
                    additionalTimeFactorsCalculator.calculateAverageFromList(additionalTimes));
        };
    }

    private PrecisionFactor getNoDataFactor(StatisticFactorName factorName, FlightPhase phase) {
        return statisticFactorCreator.getNoDataFactor(factorName, phase);
    }
}