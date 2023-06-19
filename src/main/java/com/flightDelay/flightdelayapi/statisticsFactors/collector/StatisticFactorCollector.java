package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;
import com.flightDelay.flightdelayapi.shared.exception.resource.ResourceNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class StatisticFactorCollector<T extends DelayEntityDto> {

    public List<PrecisionReport> collectFactors(String airportCode, List<T> dtos, EntityStatisticFactor[] entityFactor) {
        List<PrecisionReport> factors = new ArrayList<>();

        for (EntityStatisticFactor factorName : entityFactor) {
            try {
                PrecisionReport completeFactor = calculateFactor(factorName, dtos);
                factors.add(completeFactor);

            } catch (LackOfCrucialDataException | ResourceNotFoundException e) {
                log.warn("Unable to calculate {} due to lack of data or incorrect data for airport with ident: {}",
                        factorName,
                        airportCode);

                PrecisionReport noDataFactor = getNoDataFactor(factorName);
                factors.add(noDataFactor);
            }
        }

        return factors;
    }

    protected abstract PrecisionReport calculateFactor(EntityStatisticFactor factorName, List<T> dtos);

    protected abstract PrecisionReport getNoDataFactor(EntityStatisticFactor factorName);
}
