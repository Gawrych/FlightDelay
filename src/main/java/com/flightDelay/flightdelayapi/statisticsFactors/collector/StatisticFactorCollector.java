package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.exception.resource.ResourceNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToLackOfDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class StatisticFactorCollector {

    public List<PrecisionFactor> collectFactors(Flight flight, EntityStatisticFactor[] entityFactor) {
        List<PrecisionFactor> factors = new ArrayList<>();
        String airportIdent = flight.getAirportIdent();

        for (EntityStatisticFactor factorName : entityFactor) {
            try {
                PrecisionFactor arrivalFactor = calculateFactor(factorName, airportIdent);
                factors.add(arrivalFactor);

            } catch (UnableToCalculateDueToLackOfDataException | ResourceNotFoundException e) {
                log.warn("Unable to calculate {} due to lack of data for airport with ident: {}",
                        factorName,
                        airportIdent);

                PrecisionFactor factor = getNoDataFactor(factorName);
                factors.add(factor);
            }
        }

        return factors;
    }

    protected abstract PrecisionFactor calculateFactor(EntityStatisticFactor factorName, String airportIdent);

    protected abstract PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName);
}
