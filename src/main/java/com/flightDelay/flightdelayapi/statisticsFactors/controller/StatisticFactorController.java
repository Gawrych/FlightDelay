package com.flightDelay.flightdelayapi.statisticsFactors.controller;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticFactorController {

    @PostMapping("/airport")
    public List<WeatherFactor> getFactors(@RequestBody @Validated Flight flight,
                                          @RequestHeader("Accept-Language") String language) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));
        log.info("Language has been set to {}", Locale.forLanguageTag(language).getDisplayName());

        return new ArrayList<>();
    }
}
