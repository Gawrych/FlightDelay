package com.flightDelay.flightdelayapi.statisticsFactors.service;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.statisticsFactors.collector.AdditionalTimeFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.model.StatisticsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticFactorServiceImpl implements StatisticFactorService {

    private final AdditionalTimeFactorCollector additionalTimeFactorCollector;

    public List<StatisticsData> getFactorsByPhase(Flight flight) {
        return List.of(additionalTimeFactorCollector.getFactors(flight));
    }
}
