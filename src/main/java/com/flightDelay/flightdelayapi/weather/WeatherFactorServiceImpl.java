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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flightDelay.flightdelayapi.shared.FactorName.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherFactorServiceImpl implements WeatherFactorService {

    @Qualifier("landingFactorInfluenceQualifier")
    private final FlightPhaseFactorInfluenceQualifier landingFactorInfluenceQualifier;

    @Qualifier("takeoffFactorInfluenceQualifier")
    private final FlightPhaseFactorInfluenceQualifier takeoffFactorInfluenceQualifier;

    private final InstrumentLandingSystemCalculator instrumentLandingSystemCalculator;

    private final RunwayWeatherCalculator runwayWeatherCalculator;

    private final DelayFactorCreator delayFactorCreator;

    private final WindCalculator windCalculator;

    public List<DelayFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto) {
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);

        List<DelayFactor> delayFactors = new ArrayList<>();

        for (Map.Entry<FactorName, Integer> condition : getConditions(airportWeatherDto).entrySet()) {
            FactorName factorName = condition.getKey();
            int value = condition.getValue();

            FactorInfluence factorInfluence = checkPhaseLimit(ilsCategory, airportWeatherDto.getPhase(), factorName, value);
            delayFactors.add(delayFactorCreator.createFactor(factorName, value, factorName.getUnit(), factorInfluence));
        }

        return delayFactors;
    }

    private Map<FactorName, Integer> getConditions(AirportWeatherDto airportWeatherDto) {
        return Map.of(
                CROSSWIND, windCalculator.getCrossWind(airportWeatherDto),
                TAILWIND, windCalculator.getTailwind(airportWeatherDto),
                VISIBILITY, Math.round(airportWeatherDto.getVisibility()),
                RAIN, Math.round(airportWeatherDto.getRain()),
                CLOUDBASE, runwayWeatherCalculator
                        .calculateCloudBaseAboveRunway(airportWeatherDto, airportWeatherDto.getElevationMeters()));
    }

    private FactorInfluence checkPhaseLimit(IlsCategory ilsCategory, FlightPhase phase, FactorName name, int value) {
        return switch (phase) {
            case ARRIVAL -> landingFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
            case DEPARTURE -> takeoffFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
        };
    }
}
