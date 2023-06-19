package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayDto;
import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelayService;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.ArrivalDelayFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.ArrivalDelayFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithTextHolder;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArrivalDelayFactorCollectorImpl extends StatisticFactorCollector<ArrivalDelayDto> implements ArrivalDelayFactorCollector {

    private final ArrivalDelayService arrivalDelayService;

    private final ArrivalDelayFactorsCalculator arrivalDelayFactorsCalculator;

    private final StatisticReportCreator statisticReportCreator;

    @Override
    public List<PrecisionReport> collect(String airportIdent) {
        List<ArrivalDelayDto> arrivalDelayDtos = arrivalDelayService.findAllLatestByAirport(airportIdent);

        return super.collectFactors(airportIdent, arrivalDelayDtos, ArrivalDelayFactor.values());
    }

    @Override
    protected PrecisionReport calculateFactor(EntityStatisticFactor factorName,
                                              List<ArrivalDelayDto> arrivalDelayDtos) {

        return switch (EnumType.valueOf(ArrivalDelayFactor.class, factorName.name())) {
            case MOST_COMMON_DELAY_CAUSE -> {
                List<ValueWithTextHolder> calculatedValue = arrivalDelayFactorsCalculator
                        .calculateMostCommonDelayCause(arrivalDelayDtos);

                yield statisticReportCreator.create(factorName, calculatedValue);
            }

            case AVERAGE_TIME_TO_PARTICULAR_DELAY_CAUSE -> {
                List<ValueWithTextHolder> calculatedValue = arrivalDelayFactorsCalculator
                        .calculateAverageTimeToParticularDelayCause(arrivalDelayDtos);

                yield statisticReportCreator.create(factorName, calculatedValue);
            }
        };
    }

    @Override
    protected PrecisionReport getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticReportCreator.create(factorName);
    }
}
