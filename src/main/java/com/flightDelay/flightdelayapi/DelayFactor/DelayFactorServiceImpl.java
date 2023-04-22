package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.flight.FlightPhase;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.runway.RunwayService;
import com.flightDelay.flightdelayapi.weather.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DelayFactorServiceImpl implements DelayFactorService {

    private final ResourceBundleMessageSource messageSource;
    private final LandingLimits landingLimits;
//    private final TakingOffLimits takingOff;
    private final WeatherCalculator weatherCalculator;
    private final AirportWeatherService airportWeatherService;

    @Override
    public List<DelayFactor> getFactorsByHour(Flight flight) {
        AirportWeatherDto airportWeatherDto = airportWeatherService.getAirportWeather(flight);
        System.out.println(airportWeatherDto.getAirportIdent());
        System.out.println(airportWeatherDto.getDewPoint());
        System.out.println(airportWeatherDto.getVisibility());
        System.out.println(airportWeatherDto.getRunwaysDTO().size());
        System.out.println(airportWeatherDto.getRunwaysDTO().get(0).getHeHeadingDegT());
        System.out.println(airportWeatherDto.getRunwaysDTO().get(0).getLighted());
        System.out.println(airportWeatherDto.getRunwaysDTO().get(0).getLeElevationFt());
        System.out.println(airportWeatherDto.getRunwaysDTO().get(0).getHeElevationFt());
        System.out.println(flight.phase().name());
        List<DelayFactor> delayFactors = new ArrayList<>();
//        delayFactors.addAll(createWeatherFactors(flight));
        return delayFactors;
    }

//    public List<DelayFactor> createWeatherFactors(Flight flight) {
//        List<DelayFactor> delayFactors = new ArrayList<>();
//
//
//        Map<FactorName, Integer> conditions = weatherCalculator.getConditions(flight);
//        for (Map.Entry<FactorName, Integer> condition : conditions.entrySet()) {
//            int factorInfluence = checkFlightLimit(flight.getPhase(), condition.getKey(), condition.getValue());
//            delayFactors.add(createFactor(condition.getKey(), condition.getValue(), factorInfluence));
//        }
//
//        return delayFactors;
//    }
//
//    private int checkFlightLimit(Flight flight, FlightPhase phase, FactorName name, int value) {
//        if (phase == FlightPhase.ARRIVAL) {
//            return landingLimits.checkLimits(flight, name, value).getValue();
//
//        } else if (phase == FlightPhase.DEPARTURE) {
//            return takingOffLimits.checkLimits(name, value).getValue();
//
//        } else {
//            // TODO: Handle the exception
//            throw new UnsupportedFlightPhaseException();
//        }
//    }
//
//    private DelayFactor createFactor(FactorName factorName, int value, int influence) {
//        return DelayFactor.builder()
//                .title(getMessage(factorName.name()))
//                .influenceOnDelay(influence)
//                .value(value)
//                .build();
//    }
//
//    public String getMessage(String title) {
//        // TODO: Handle the exception
//        return messageSource.getMessage(title, null, LocaleContextHolder.getLocale());
//    }
}
