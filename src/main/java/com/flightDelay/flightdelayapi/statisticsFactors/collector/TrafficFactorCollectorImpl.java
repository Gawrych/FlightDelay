package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.statisticsFactors.calculator.TrafficFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.TrafficFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import com.flightDelay.flightdelayapi.statisticsFactors.model.ValueWithDateHolder;
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

    private final StatisticReportCreator statisticReportCreator;

    @Override
    public List<PrecisionReport> collect(String airportCode) {
        List<TrafficDto> trafficDtos = trafficService.findAllLatestByAirport(airportCode);

        return super.collectFactors(airportCode, trafficDtos, TrafficFactor.values());
    }

    @Override
    protected PrecisionReport calculateFactor(EntityStatisticFactor factorName, List<TrafficDto> trafficDtos) {
        return switch (EnumType.valueOf(TrafficFactor.class, factorName.name())) {
            case TOP_MONTH_OF_TRAFFIC -> {
                ValueWithDateHolder calculatedValue = trafficFactorsCalculator.calculateTopMonth(trafficDtos);
                yield statisticReportCreator.create(factorName, calculatedValue);
            }

            case AVERAGE_MONTHLY_TRAFFIC -> {
                double calculatedValue = trafficFactorsCalculator.calculateAverageMonthly(trafficDtos);
                yield statisticReportCreator.create(factorName, calculatedValue);
            }
        };
    }

    @Override
    protected PrecisionReport getNoDataFactor(EntityStatisticFactor factorName) {
        return statisticReportCreator.create(factorName);
    }
}