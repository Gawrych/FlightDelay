package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.DelayFactor.DelayFactorCreator;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.shared.FlightPhase;
import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flightDelay.flightdelayapi.shared.FactorName.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherFactorServiceImpl implements WeatherFactorService {

    private final InstrumentLandingSystemCalculator instrumentLandingSystemCalculator;

    private final WeatherCalculator weatherCalculator;

    private final LandingLimits landingLimits;

    private final DelayFactorCreator delayFactorCreator;

    public List<DelayFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto) {
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);

        List<DelayFactor> delayFactors = new ArrayList<>();
        Map<FactorName, Integer> conditions = getConditions(airportWeatherDto);

        for (Map.Entry<FactorName, Integer> condition : conditions.entrySet()) {
            FactorInfluence factorInfluence = checkPhaseLimit(ilsCategory, airportWeatherDto.getPhase(), condition.getKey(), condition.getValue());
            delayFactors.add(delayFactorCreator.createFactor(condition.getKey(), condition.getValue(), factorInfluence));
        }

        return delayFactors;
    }

    private Map<FactorName, Integer> getConditions(AirportWeatherDto airportWeatherDto) {
        return Map.of(
                CROSSWIND, weatherCalculator.getCrossWind(airportWeatherDto),
                TAILWIND, weatherCalculator.getTailwind(airportWeatherDto));
    }

    private FactorInfluence checkPhaseLimit(IlsCategory ilsCategory, FlightPhase phase, FactorName name, int value) {
        return switch (phase) {
            case ARRIVAL -> landingLimits.checkLimits(name, value, ilsCategory);
            case DEPARTURE -> FactorInfluence.LOW;
        };
    }
}
