package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.weather.LandingLimitsService;
import com.flightDelay.flightdelayapi.weather.Weather;
import com.flightDelay.flightdelayapi.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DelayFactorServiceImpl implements DelayFactorService {

    private final ResourceBundleMessageSource messageSource;
    private final LandingLimitsService landingLimitsService;
    private final WeatherService weatherService;
    private final AirportService airportService;

    @Override
    public List<DelayFactor> getFactorsByHour(String airportIdent, Date time) {
        Airport airport = airportService.findByAirportIdent(airportIdent);
        weatherService.setWeather(airport.getAirportIdent(), time.getTime());
        weatherService.setAirport(airport);

        List<DelayFactor> delayFactors = new ArrayList<>();
        delayFactors.add(getCrossWindFactor());
        return delayFactors;
    }

    public DelayFactor getCrossWindFactor() {
        int crossWind = weatherService.getCrossWind();
        int ilsCategory = weatherService.getIlsCategory();
        int influence = landingLimitsService.checkCrossWindLimits(crossWind, ilsCategory);


        return DelayFactor.builder()
                .title(getMessage("crossWind"))
                .influenceOnDelay(influence)
                .value(crossWind)
                .build();
    }

    public String getMessage(String title) {
        return messageSource.getMessage(title, null, LocaleContextHolder.getLocale());
    }
}
