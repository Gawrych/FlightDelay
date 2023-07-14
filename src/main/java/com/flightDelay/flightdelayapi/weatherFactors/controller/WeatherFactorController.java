package com.flightDelay.flightdelayapi.weatherFactors.controller;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.PrecisionTimeFlight;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.service.WeatherFactorService;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactorsPeriod;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/v1/weather", produces="application/json")
public class WeatherFactorController {

    private final WeatherFactorService weatherFactorService;

    @PostMapping("/hour")
    @ResponseStatus(HttpStatus.OK)
    public Map<WeatherFactorName, WeatherFactor> getFactorsToSpecifyTime(@RequestBody @Validated PrecisionTimeFlight precisionTimeFlight,
                                                                         @RequestHeader(
                                                               value = "Accept-Language",
                                                               defaultValue = "en-US") String language) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));

        log.debug("Result language has been set to {}", Locale.forLanguageTag(language).getDisplayName());

        return weatherFactorService.getFactorsByHour(precisionTimeFlight);
    }

    @PostMapping("/periods")
    @ResponseStatus(HttpStatus.OK)
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
