package com.flightDelay.flightdelayapi.weatherFactors.collector;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.enums.FlightPhase;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.InstrumentLandingSystemCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.RunwayWeatherCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.calculator.WindCalculator;
import com.flightDelay.flightdelayapi.weatherFactors.creator.WeatherFactorCreator;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.exception.IllegalFlightPhaseException;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import com.flightDelay.flightdelayapi.weatherFactors.qualifier.FlightPhaseFactorInfluenceQualifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    @Override
    public Map<WeatherFactorName, WeatherFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto) {
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);

        Map<WeatherFactorName, WeatherFactor> weatherFactors = new HashMap<>();

        for (Map.Entry<WeatherFactorName, Integer> condition : getConditions(airportWeatherDto).entrySet()) {
            WeatherFactorName weatherFactorName = condition.getKey();
            int value = condition.getValue();

            FactorInfluence factorInfluence = checkPhaseLimit(
                    ilsCategory,
                    airportWeatherDto.getPhase(),
                    weatherFactorName,
                    value);

            WeatherFactor factor = weatherFactorCreator.createFactor(weatherFactorName, value, factorInfluence);

            log.info("For {} factor with value {} {} has been set delay influence on {}",
                    factor.getTitle(),
                    factor.getValue(),
                    factor.getUnitSymbol(),
                    factor.getInfluenceOnDelay());

            weatherFactors.put(factor.getId(), factor);
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
            default -> throw new IllegalFlightPhaseException(phase.name());
        };
    }
}
