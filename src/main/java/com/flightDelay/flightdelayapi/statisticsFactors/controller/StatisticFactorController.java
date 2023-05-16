package com.flightDelay.flightdelayapi.statisticsFactors.controller;

import com.flightDelay.flightdelayapi.shared.validator.AirportIcaoCodeValidator;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticFactorController {


    @GetMapping("/airport")
    public List<WeatherFactor> getFactors(@RequestParam("icao") @AirportIcaoCodeValidator String airportIdent,
                                          @RequestHeader("Accept-Language") String acceptLanguage) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(acceptLanguage));
        return new ArrayList<>();
    }
}
