package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.exception.resource.PreDepartureDelayDataNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.TrafficFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.TrafficFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import com.flightDelay.flightdelayapi.traffic.TrafficService;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrafficFactorCollectorImpl extends StatisticFactorCollector implements TrafficFactorCollector {

    private final TrafficService trafficService;

    private final TrafficFactorsCalculator trafficFactorsCalculator;

    private final StatisticFactorCreator statisticFactorCreator;

    @Override
    public List<PrecisionFactor> collect(Flight flight) {
        return super.collectFactors(flight, TrafficFactor.values());
    }

    // TODO: Try change this class to more generic
    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName, String airportIdent)
            throws UnableToCalculateDueToLackOfDataException, PreDepartureDelayDataNotFoundException {

        List<TrafficDto> additionalTimes = trafficService.findAllLatestByAirport(airportIdent);

        log.info("{} traffic records have been found in the database for airport: {}",
                additionalTimes.size(),
                airportIdent);


        return switch (EnumType.valueOf(TrafficFactor.class, factorName.name())) {
            case AVERAGE_MONTHLY_DEPARTURES_TRAFFIC -> statisticFactorCreator.createSimpleValue(
                    factorName,
                    trafficFactorsCalculator.calculateAverageMonthlyAmountOfDepartures(additionalTimes));

            case AVERAGE_MONTHLY_ARRIVAL_TRAFFIC -> statisticFactorCreator.createSimpleValue(
                    factorName,
                    trafficFactorsCalculator.calculateAverageMonthlyAmountOfArrival(additionalTimes));
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.createNoDataFactor(factorName);
    }
}
