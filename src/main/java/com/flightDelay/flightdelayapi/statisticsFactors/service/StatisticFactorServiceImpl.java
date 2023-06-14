package com.flightDelay.flightdelayapi.statisticsFactors.service;

import com.flightDelay.flightdelayapi.statisticsFactors.collector.AdditionalTimeFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.collector.ArrivalDelayFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.collector.PreDepartureFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.collector.TrafficFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticFactorServiceImpl implements StatisticFactorService {

    private final AdditionalTimeFactorCollector additionalTimeFactorCollector;

    private final PreDepartureFactorCollector preDepartureFactorCollector;

    private final ArrivalDelayFactorCollector arrivalDelayFactorCollector;

    private final TrafficFactorCollector trafficFactorCollector;

    public Map<String, PrecisionFactor> getFactorsByPhase(String airportIdent) {
        List<PrecisionFactor> factors = new ArrayList<>();

        factors.addAll(preDepartureFactorCollector.collect(airportIdent));
        factors.addAll(additionalTimeFactorCollector.collect(airportIdent));
        factors.addAll(arrivalDelayFactorCollector.collect(airportIdent));
        factors.addAll(trafficFactorCollector.collect(airportIdent));

        return factors.stream().collect(Collectors.toMap(key -> key.getId().name(), Function.identity()));
    }
}
