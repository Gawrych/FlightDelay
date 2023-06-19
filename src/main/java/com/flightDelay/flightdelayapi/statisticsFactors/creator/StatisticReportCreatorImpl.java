package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorStatus;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorType;
import com.flightDelay.flightdelayapi.statisticsFactors.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatisticReportCreatorImpl implements StatisticReportCreator {

    @Value("${date.defaultDatePattern}")
    private String defaultDatePattern;

    @Value("${date.dateWithoutDayPattern}")
    private String dateWithoutDayPattern;

    @Value("${statistics.precisionInResponse}")
    private int precision;

    private final ResourceBundleMessageSource messageSource;

    @Override
    public PrecisionReport create(EntityStatisticFactor factorName,
                                  double value) {
        return SimpleValueStatisticReport.builder()
                .id(factorName)
                .name(getMessage(factorName))
                .unitSymbol(factorName.getUnit().getUnit())
                .value(setPrecision(value))
                .phase(factorName.getPhase())
                .factorType(factorName.getType())
                .status(StatisticFactorStatus.COMPLETE)
                .build();
    }

    @Override
    public PrecisionReport create(EntityStatisticFactor factorName,
                                  List<ValueWithTextHolder> values) {
        return TopValueWithTextStatisticReport.builder()
                .id(factorName)
                .name(getMessage(factorName))
                .unitSymbol(factorName.getUnit().getUnit())
                .values(values)
                .phase(factorName.getPhase())
                .factorType(factorName.getType())
                .status(StatisticFactorStatus.COMPLETE)
                .build();

    }

    @Override
    public PrecisionReport create(EntityStatisticFactor factorName,
                                  ValueWithDateHolder value) {

         String datePattern = factorName.getType() == StatisticFactorType.TOP_VALUE_WITH_PRECISION_DATE
                ? defaultDatePattern
                : dateWithoutDayPattern;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);

        return ValueWithTextStatisticReport.builder()
                .id(factorName)
                .name(getMessage(factorName))
                .unitSymbol(factorName.getUnit().getUnit())
                .value(setPrecision(value.getValue()))
                .date(value.getDate().format(formatter))
                .phase(factorName.getPhase())
                .factorType(factorName.getType())
                .status(StatisticFactorStatus.COMPLETE)
                .build();
    }

    @Override
    public PrecisionReport create(EntityStatisticFactor factorName) {
        return StatisticReport.builder()
                .id(factorName)
                .name(getMessage(factorName))
                .unitSymbol(factorName.getUnit().getUnit())
                .phase(factorName.getPhase())
                .factorType(factorName.getType())
                .status(StatisticFactorStatus.NO_DATA)
                .build();
    }

    private double setPrecision(double value) {
        return Precision.round(value, precision);
    }

    private String getMessage(EntityStatisticFactor factorName) {
        return messageSource.getMessage(
                factorName.name(),
                null,
                LocaleContextHolder.getLocale());
    }
}
