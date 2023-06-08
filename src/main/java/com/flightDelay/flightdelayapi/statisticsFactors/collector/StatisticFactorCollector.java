package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.exception.UnableToCalculateDueToIncorrectDataException;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class StatisticFactorCollector<T> {

    public List<PrecisionFactor> collectFactors(String airportIdent, List<T> dtos, EntityStatisticFactor[] entityFactor) {
        List<PrecisionFactor> factors = new ArrayList<>();

        for (EntityStatisticFactor factorName : entityFactor) {
            try {
                PrecisionFactor arrivalFactor = calculateFactor(factorName, dtos);
                factors.add(arrivalFactor);

            } catch (UnableToCalculateDueToIncorrectDataException | ConstraintViolationException e) {
                log.warn("Unable to calculate {} due to lack of data or incorrect data for airport with ident: {}",
                        factorName,
                        airportIdent);

                PrecisionFactor factor = getNoDataFactor(factorName);
                factors.add(factor);
            }
        }

        return factors;
    }

    protected abstract PrecisionFactor calculateFactor(EntityStatisticFactor factorName, List<T> dtos);

    protected abstract PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName);
}
