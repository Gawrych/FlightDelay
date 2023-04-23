package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.DelayFactor.DelayFactorCreator;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.flight.FlightPhase;
import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherFactorServiceImpl implements WeatherFactorService {

    private final InstrumentLandingSystemCalculator instrumentLandingSystemCalculator;

    private final WeatherCalculator weatherCalculator;

    private final LandingLimits landingLimits;

    private final DelayFactorCreator delayFactorCreator;

    public List<DelayFactor> getWeatherFactors(AirportWeatherDto airportWeatherDto) {
        List<DelayFactor> delayFactors = new ArrayList<>();

        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getCategory(airportWeatherDto);

        Map<FactorName, Integer> conditions = getConditions(airportWeatherDto);
        for (Map.Entry<FactorName, Integer> condition : conditions.entrySet()) {
            FactorInfluence factorInfluence = checkPhaseLimit(ilsCategory, airportWeatherDto.getPhase(), condition.getKey(), condition.getValue());
            delayFactors.add(delayFactorCreator.createFactor(condition.getKey(), condition.getValue(), factorInfluence));
        }

        return delayFactors;
    }

    public Map<FactorName, Integer> getConditions(AirportWeatherDto airportWeatherDto) {

        return Map.of(
                FactorName.CROSSWIND, weatherCalculator.getCrossWind(airportWeatherDto),
                FactorName.HEADWIND, weatherCalculator.getHeadWind(airportWeatherDto));
    }

    private FactorInfluence checkPhaseLimit(IlsCategory ilsCategory, FlightPhase phase, FactorName name, int value) {
        if (phase == FlightPhase.ARRIVAL) {
            return landingLimits.checkLimits(name, value, ilsCategory);

        } else if (phase == FlightPhase.DEPARTURE) {
//            return takingOffLimits.checkLimits(name, value).getValue();
            return FactorInfluence.LOW;
        } else {
            // TODO: Log why is unknown "Support for this type do not exist"
            return FactorInfluence.UNKNOWN;
        }
    }
}
