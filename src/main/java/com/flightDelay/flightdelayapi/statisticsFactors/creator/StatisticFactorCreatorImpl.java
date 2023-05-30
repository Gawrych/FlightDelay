package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.FactorStatus;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorName;
import com.flightDelay.flightdelayapi.statisticsFactors.model.*;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticFactorCreatorImpl implements StatisticFactorCreator {

    private final ResourceBundleMessageSource messageSource;

    @Value("${statistics.precisionInResponse}")
    private int precision;

    @Override
    public PrecisionFactor createAverage(StatisticFactorName factorName,
                                        FlightPhase phase,
                                        double value) {
        return AverageStatisticFactor.builder()
                .id(factorName)
                .name(getMessage(factorName, phase))
                .unit(factorName.getUnit())
                .value(setPrecision(value))
                .status(FactorStatus.COMPLETE)
                .build();
    }

    @Override
    public PrecisionFactor createTopMonth(StatisticFactorName factorName,
                                          FlightPhase phase,
                                          TopMonthValueHolder value) {
        return TopMonthStatisticFactor.builder()
                .id(factorName)
                .name(getMessage(factorName, phase))
                .unit(factorName.getUnit())
                .value(setPrecision(value.getValue()))
                .monthName(value.getMonthName())
                .status(FactorStatus.COMPLETE)
                .build();
    }

    @Override
    public PrecisionFactor getNoDataFactor(StatisticFactorName factorName, FlightPhase phase) {
        return StatisticFactor.builder()
                .id(factorName)
                .name(getMessage(factorName, phase))
                .unit(factorName.getUnit())
                .status(FactorStatus.NO_DATA)
                .build();
    }

    private double setPrecision(double value) {
        return Precision.round(value, precision);
    }

    private String getMessage(StatisticFactorName factorName, FlightPhase phase) {
        String flightPhase = messageSource.getMessage(
                phase.name(),
                null,
                LocaleContextHolder.getLocale());

        return messageSource.getMessage(
                factorName.name() + "_" + factorName.getDelayPhase(),
                new Object[]{flightPhase},
                LocaleContextHolder.getLocale());
    }
}
