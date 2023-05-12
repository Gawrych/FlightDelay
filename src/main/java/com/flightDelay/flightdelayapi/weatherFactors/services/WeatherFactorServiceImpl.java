package com.flightDelay.flightdelayapi.weatherFactors.services;

import com.flightDelay.flightdelayapi.delayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.delayFactor.DelayFactorCreator;
import com.flightDelay.flightdelayapi.weatherFactors.dtos.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FactorName;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.weatherFactors.calculators.InstrumentLandingSystemCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.calculators.RunwayWeatherCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.calculators.WindCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.qualifiers.FlightPhaseFactorInfluenceQualifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flightDelay.flightdelayapi.weatherFactors.enums.FactorName.*;

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
                VISIBILITY, Math.round(airportWeatherDto.getWeather().getVisibilityM()),
                RAIN, Math.round(airportWeatherDto.getWeather().getRainMm()),
                CLOUDBASE, runwayWeatherCalculator.calculateCloudBase(airportWeatherDto, airportWeatherDto.getElevationM()));
    }

    private FactorInfluence checkPhaseLimit(IlsCategory ilsCategory, FlightPhase phase, FactorName name, int value) {
        return switch (phase) {
            case ARRIVAL -> landingFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
            case DEPARTURE -> takeoffFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
        };
    }
}
