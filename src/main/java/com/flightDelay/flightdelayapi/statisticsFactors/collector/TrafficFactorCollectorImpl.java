package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.statisticsFactors.calculator.TrafficFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticFactorCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.TrafficFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import com.flightDelay.flightdelayapi.traffic.TrafficService;
import jakarta.persistence.EnumType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrafficFactorCollectorImpl extends StatisticFactorCollector<TrafficDto> implements TrafficFactorCollector {

    private final TrafficService trafficService;

    private final TrafficFactorsCalculator trafficFactorsCalculator;

    private final StatisticFactorCreator statisticFactorCreator;

    @Override
    public List<PrecisionFactor> collect(String airportIdent) {
        List<TrafficDto> trafficDtos = trafficService.findAllLatestByAirport(airportIdent);

        return super.collectFactors(airportIdent, trafficDtos, TrafficFactor.values());
    }

    @Override
    protected PrecisionFactor calculateFactor(EntityStatisticFactor factorName, List<TrafficDto> trafficDtos) {
        return switch (EnumType.valueOf(TrafficFactor.class, factorName.name())) {
            case TOP_MONTH_OF_TRAFFIC -> statisticFactorCreator.createValueWithDate(
                    factorName,
                    trafficFactorsCalculator.calculateTopMonth(trafficDtos));

            case AVERAGE_MONTHLY_TRAFFIC -> statisticFactorCreator.createSimpleValue(
                    factorName,
                    trafficFactorsCalculator.calculateAverageMonthly(trafficDtos));
        };
    }

    @Override
    protected PrecisionFactor getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticFactorCreator.createNoDataFactor(factorName);
    }
}
