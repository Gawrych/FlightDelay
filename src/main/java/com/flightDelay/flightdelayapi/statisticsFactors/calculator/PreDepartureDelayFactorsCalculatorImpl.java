package com.flightDelay.flightdelayapi.statisticsFactors.calculator;

import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelay;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PreDepartureDelayFactorsCalculatorImpl implements PreDepartureDelayFactorsCalculator {

    private final AverageFactorCalculator averageFactorCalculator;

    private final PreDepartureDelayService preDepartureDelayService;

    public double calculateAverageDelayTime(String airportIdent) {
        List<PreDepartureDelay> allByAirportWithDateAfter = preDepartureDelayService.findAllLatestByAirport(airportIdent);
        log.info("{} pre departure delay records have been found in database", allByAirportWithDateAfter.size());

        if (allByAirportWithDateAfter.size() == 0) {
            log.info("Lack of data for airport with ident: {}", airportIdent);
        }

        List<Integer> numerator = allByAirportWithDateAfter.stream().map(PreDepartureDelay::getDelayInMinutes).toList();
        List<Integer> denominator = allByAirportWithDateAfter.stream().map(PreDepartureDelay::getNumberOfDepartures).toList();

        return averageFactorCalculator.calculateAverage(numerator, denominator);
    }

//    public double calculateTheBiggestPreDepartureDelayDay(String airportIdent) {
//        List<AdditionalTime> allByAirportWithDateAfter = additionalTimeService.findAllLatestDepartureAdditionalTimeByAirport(airportIdent);
//        log.info("{} additional time records have been found in database", allByAirportWithDateAfter.size());
//    }
}
