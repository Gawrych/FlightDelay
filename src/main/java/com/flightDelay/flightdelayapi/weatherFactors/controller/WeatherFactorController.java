package com.flightDelay.flightdelayapi.weatherFactors.controller;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.PrecisionTimeFlight;
import com.flightDelay.flightdelayapi.weatherFactors.service.WeatherFactorService;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactorsPeriod;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/weather")
public class WeatherFactorController {

    private final WeatherFactorService weatherFactorService;

    @PostMapping("/hour")
    public List<WeatherFactor> getFactorsToSpecifyTime(@RequestBody @Validated PrecisionTimeFlight precisionTimeFlight,
                                                       @RequestHeader(
                                                               value = "Accept-Language",
                                                               defaultValue = "en-US") String language) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));
        log.debug("Result language has been set to {}", Locale.forLanguageTag(language).getDisplayName());

        return weatherFactorService.getFactorsByHour(precisionTimeFlight);
    }

    @PostMapping("/periods")
    public List<WeatherFactorsPeriod> getFactorsForNextDay(@RequestBody @Validated Flight flight,
                                                           @RequestParam("days") int amountOfDays,
                                                           @RequestHeader(
                                                                   value = "Accept-Language",
                                                                   defaultValue = "en-US") String language) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));
        log.debug("Result language has been set to {}", Locale.forLanguageTag(language).getDisplayName());

        return weatherFactorService.getFactorsInPeriods(flight, amountOfDays);
    }
}
