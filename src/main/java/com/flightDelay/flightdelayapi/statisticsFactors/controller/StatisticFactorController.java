package com.flightDelay.flightdelayapi.statisticsFactors.controller;

import com.flightDelay.flightdelayapi.shared.Flight;
import com.flightDelay.flightdelayapi.statisticsFactors.model.StatisticsData;
import com.flightDelay.flightdelayapi.statisticsFactors.service.StatisticFactorService;
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
@RequestMapping("/api/v1/statistics")
public class StatisticFactorController {

    public final StatisticFactorService statisticFactorService;

    @PostMapping("/phase")
    public List<StatisticsData> getFactors(@RequestBody @Validated Flight flight,
                                           @RequestHeader(
                                                 value = "Accept-Language",
                                                 defaultValue = "en-US") String language) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));
        log.debug("Result language has been set to {}", Locale.forLanguageTag(language).getDisplayName());


        return statisticFactorService.getFactorsByPhase(flight);
    }
}
