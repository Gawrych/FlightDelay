package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.FactorStatus;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorName;
import com.flightDelay.flightdelayapi.statisticsFactors.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticFactorCreatorImpl implements StatisticFactorCreator {

    private final ResourceBundleMessageSource messageSource;

    @Override
    public PrecisionFactor createAverage(StatisticFactorName factorName,
                                        double value) {
        return AverageStatisticFactor.builder()
                .id(factorName)
                .name(getMessage(factorName))
                .unit(factorName.getUnit())
                .value(value)
                .status(FactorStatus.COMPLETE)
                .build();
    }

    @Override
    public PrecisionFactor createTopMonth(StatisticFactorName factorName,
                                          TopMonthValueHolder value) {
        return TopMonthStatisticFactor.builder()
                .id(factorName)
                .name(getMessage(factorName))
                .unit(factorName.getUnit())
                .value(value.getValue())
                .monthName(value.getMonthName())
                .status(FactorStatus.COMPLETE)
                .build();
    }

    @Override
    public PrecisionFactor getNoDataFactor(StatisticFactorName factorName) {
        return StatisticFactor.builder()
                .id(factorName)
                .name(getMessage(factorName))
                .unit(factorName.getUnit())
                .status(FactorStatus.NO_DATA)
                .build();
    }

    private String getMessage(StatisticFactorName factorName) {
        return messageSource.getMessage(
                factorName.name() + "_" + factorName.getDelayPhase(),
                null,
                LocaleContextHolder.getLocale());
    }
}
