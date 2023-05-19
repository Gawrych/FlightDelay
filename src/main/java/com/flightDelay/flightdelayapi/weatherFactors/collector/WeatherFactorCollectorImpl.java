package com.flightDelay.flightdelayapi.weatherFactors.collector;

import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import com.flightDelay.flightdelayapi.weatherFactors.creator.WeatherFactorCreator;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;
import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.InstrumentLandingSystemCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.RunwayWeatherCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.WindCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.qualifier.FlightPhaseFactorInfluenceQualifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherFactorCollectorImpl implements WeatherFactorCollector {

    @Qualifier("landingFactorInfluenceQualifier")
    private final FlightPhaseFactorInfluenceQualifier landingFactorInfluenceQualifier;

    @Qualifier("takeoffFactorInfluenceQualifier")
    private final FlightPhaseFactorInfluenceQualifier takeoffFactorInfluenceQualifier;

    private final InstrumentLandingSystemCalculator instrumentLandingSystemCalculator;

    private final RunwayWeatherCalculator runwayWeatherCalculator;

    private final WeatherFactorCreator weatherFactorCreator;

    private final WindCalculator windCalculator;

    public List<WeatherFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto) {
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);

        List<WeatherFactor> weatherFactors = new ArrayList<>();

        for (Map.Entry<WeatherFactorName, Integer> condition : getConditions(airportWeatherDto).entrySet()) {
            WeatherFactorName weatherFactorName = condition.getKey();
            int value = condition.getValue();

            FactorInfluence factorInfluence = checkPhaseLimit(ilsCategory, airportWeatherDto.getPhase(), weatherFactorName, value);
            weatherFactors.add(weatherFactorCreator.createFactor(weatherFactorName, value, factorInfluence));
        }

        return weatherFactors;
    }

    private Map<WeatherFactorName, Integer> getConditions(AirportWeatherDto airportWeatherDto) {
        return Map.of(
                CROSSWIND, windCalculator.getCrossWind(airportWeatherDto),
                TAILWIND, windCalculator.getTailwind(airportWeatherDto),
                VISIBILITY, Math.round(airportWeatherDto.getWeather().getVisibilityM()),
                RAIN, Math.round(airportWeatherDto.getWeather().getRainMm()),
                CLOUDBASE, runwayWeatherCalculator.calculateCloudBase(
                        airportWeatherDto.getWeather().getTemperature(),
                        airportWeatherDto.getWeather().getDewPoint(),
                        airportWeatherDto.getElevationM()));
    }

    private FactorInfluence checkPhaseLimit(IlsCategory ilsCategory, FlightPhase phase, WeatherFactorName name, int value) {
        return switch (phase) {
            case ARRIVAL -> landingFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
            case DEPARTURE -> takeoffFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
        };
    }
}
