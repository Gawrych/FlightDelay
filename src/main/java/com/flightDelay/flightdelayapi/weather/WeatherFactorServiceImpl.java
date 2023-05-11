package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.DelayFactor.DelayFactorCreator;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.hourResponse.HourFactors;
import com.flightDelay.flightdelayapi.shared.FlightPhase;
import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.WeatherFactor;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.flightDelay.flightdelayapi.shared.WeatherFactor.*;

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

    @Override
    public HourFactors getWeatherHourFactors(AirportWeatherDto airportWeatherDto) {
        List<DelayFactor> factors = getWeatherFactors(airportWeatherDto);
        return new HourFactors(airportWeatherDto.getTime(), factors);
    }

    @Override
    public List<DelayFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto) {
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);

        List<DelayFactor> delayFactors = new ArrayList<>();

        for (Map.Entry<WeatherFactor, Integer> condition : getConditions(airportWeatherDto).entrySet()) {
            WeatherFactor weatherFactor = condition.getKey();
            int value = condition.getValue();

            FactorInfluence factorInfluence = checkPhaseLimit(ilsCategory, airportWeatherDto.getPhase(), weatherFactor, value);
            delayFactors.add(delayFactorCreator.createFactor(weatherFactor, value, weatherFactor.getUnit(), factorInfluence));
        }

        return delayFactors;
    }

    private Map<WeatherFactor, Integer> getConditions(AirportWeatherDto airportWeatherDto) {
        return Map.of(
                CROSSWIND, windCalculator.getCrossWind(airportWeatherDto),
                TAILWIND, windCalculator.getTailwind(airportWeatherDto),
                VISIBILITY, Math.round(airportWeatherDto.getVisibility()),
                RAIN, Math.round(airportWeatherDto.getRain()),
                CLOUDBASE, runwayWeatherCalculator
                        .calculateCeilingAboveRunway(airportWeatherDto, airportWeatherDto.getElevationMeters()));
    }

    private FactorInfluence checkPhaseLimit(IlsCategory ilsCategory, FlightPhase phase, WeatherFactor name, int value) {
        return switch (phase) {
            case ARRIVAL -> landingFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
            case DEPARTURE -> takeoffFactorInfluenceQualifier.checkLimits(name, value, ilsCategory);
        };
    }
}
