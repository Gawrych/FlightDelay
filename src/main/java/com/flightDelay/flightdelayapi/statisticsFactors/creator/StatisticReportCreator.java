package com.flightDelay.flightdelayapi.statisticsFactors.creator;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;

import java.util.List;

public interface StatisticReportCreator {

    PrecisionReport create(EntityStatisticFactor factorName);

    PrecisionReport create(EntityStatisticFactor factorName, double value);

    PrecisionReport create(EntityStatisticFactor factorName, ValueWithDateHolder value);

    PrecisionReport create(EntityStatisticFactor factorName, List<ValueWithTextHolder> values);
}
