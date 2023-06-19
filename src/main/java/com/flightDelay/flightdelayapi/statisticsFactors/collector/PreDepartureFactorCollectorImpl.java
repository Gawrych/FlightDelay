package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayService;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.PreDepartureDelayFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.PreDepartureDelayFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreDepartureFactorCollectorImpl extends StatisticFactorCollector<PreDepartureDelayDto> implements PreDepartureFactorCollector {

    private final PreDepartureDelayService preDepartureDelayService;

    private final PreDepartureDelayFactorsCalculator preDepartureDelayFactorsCalculator;

    private final StatisticReportCreator statisticReportCreator;

    @Override
    public List<PrecisionReport> collect(String airportCode) {
        List<PreDepartureDelayDto> preDepartureDelayDtos = preDepartureDelayService.findAllLatestByAirport(airportCode);

        return super.collectFactors(airportCode, preDepartureDelayDtos, PreDepartureDelayFactor.values());
    }

    @Override
    protected PrecisionReport calculateFactor(EntityStatisticFactor factorName,
                                              List<PreDepartureDelayDto> preDepartureDelayDtos) {

        return switch (EnumType.valueOf(PreDepartureDelayFactor.class, factorName.name())) {
            case TOP_MONTH_OF_PRE_DEPARTURE_DELAY -> {
                ValueWithDateHolder calculatedValue = preDepartureDelayFactorsCalculator
                        .calculateTopMonthDelay(preDepartureDelayDtos);

                yield statisticReportCreator.create(factorName, calculatedValue);
            }

            case TOP_DAY_OF_PRE_DEPARTURE_DELAY -> {
                ValueWithDateHolder calculatedValue = preDepartureDelayFactorsCalculator
                        .calculateTopDayDelay(preDepartureDelayDtos);

                yield statisticReportCreator.create(factorName, calculatedValue);

            }

            case AVERAGE_PRE_DEPARTURE_DELAY -> {
                double calculatedValue = preDepartureDelayFactorsCalculator
                        .calculateAverageDelayTime(preDepartureDelayDtos);

                yield statisticReportCreator.create(factorName, calculatedValue);
            }
        };
    }

    @Override
    protected PrecisionReport getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticReportCreator.create(factorName);
    }
}
