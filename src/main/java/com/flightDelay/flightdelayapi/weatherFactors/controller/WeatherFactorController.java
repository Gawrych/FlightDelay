package com.flightDelay.flightdelayapi.weatherFactors.controller;

import com.flightDelay.flightdelayapi.weatherFactors.service.WeatherFactorService;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactorsPeriod;
import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather")
public class WeatherFactorController {

    private final WeatherFactorService weatherFactorService;

    @PostMapping("/hour")
    public List<WeatherFactor> getFactorsToSpecifyTime(@RequestBody @Validated Flight flight,
                                                       @RequestHeader("Accept-Language") String acceptLanguage) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(acceptLanguage));
        return weatherFactorService.getFactorsByHour(flight);
    }

    @PostMapping("/periods")
    public List<WeatherFactorsPeriod> getFactorsForNextDay(@RequestBody @Validated Flight flight,
                                                           @RequestParam("days") int amountOfDays,
                                                           @RequestHeader("Accept-Language") String acceptLanguage) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(acceptLanguage));
        return weatherFactorService.getFactorsInPeriods(flight, amountOfDays);
    }
}
