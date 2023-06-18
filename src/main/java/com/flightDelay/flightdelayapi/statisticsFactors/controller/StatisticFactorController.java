package com.flightDelay.flightdelayapi.statisticsFactors.controller;

import com.flightDelay.flightdelayapi.shared.validator.AirportIcaoCodeValidator;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.service.StatisticFactorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/statistics", produces="application/json")
public class StatisticFactorController {

    public final StatisticFactorService statisticFactorService;

    @PostMapping("/{airportCode}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, PrecisionFactor> getFactors(@PathVariable @AirportIcaoCodeValidator String airportCode,
                                                   @RequestHeader(
                                                           value = "Accept-Language",
                                                           defaultValue = "en-US") String language) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));

        log.info("Result language has been set to {}", Locale.forLanguageTag(language).getDisplayName());

        return statisticFactorService.getFactorsByPhase(airportCode);
    }
}
