package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayDto;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayService;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.PreDepartureDelayFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.PreDepartureDelayFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
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

    private final StatisticFactorCreator statisticFactorCreator;

    @Override
    public List<PrecisionFactor> collect(String airportIdent) {
        List<PreDepartureDelayDto> preDepartureDelayDtos = preDepartureDelayService.findAllLatestByAirport(airportIdent);

        return super.collectFactors(airportIdent, preDepartureDelayDtos, PreDepartureDelayFactor.values());
    }

    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName,
                                              List<PreDepartureDelayDto> preDepartureDelayDtos) {

        return switch (EnumType.valueOf(PreDepartureDelayFactor.class, factorName.name())) {
            case TOP_MONTH_OF_PRE_DEPARTURE_DELAY -> statisticFactorCreator.createValueWithDate(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateTopMonthDelay(preDepartureDelayDtos));

            case TOP_DAY_OF_PRE_DEPARTURE_DELAY -> statisticFactorCreator.createValueWithDate(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateTopDayDelay(preDepartureDelayDtos));

            case AVERAGE_PRE_DEPARTURE_DELAY -> statisticFactorCreator.createSimpleValue(
                    factorName,
                    preDepartureDelayFactorsCalculator.calculateAverageDelayTime(preDepartureDelayDtos));
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.createNoDataFactor(factorName);
    }
}
