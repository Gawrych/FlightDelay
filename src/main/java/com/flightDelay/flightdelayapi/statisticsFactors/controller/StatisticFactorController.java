package com.flightDelay.flightdelayapi.statisticsFactors.controller;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.shared.validator.AirportIcaoCodeValidator;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticFactorController {


    @GetMapping("/airport")
    public List<WeatherFactor> getFactors(@RequestBody @Validated Flight flight,
                                          @RequestHeader("Language") String language) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));
        return new ArrayList<>();
    }
}
