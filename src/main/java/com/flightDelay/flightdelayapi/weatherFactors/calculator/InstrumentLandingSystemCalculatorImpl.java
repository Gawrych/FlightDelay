package com.flightDelay.flightdelayapi.weatherFactors.calculator;
import com.flightDelay.flightdelayapi.runway.dto.RunwayWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import com.flightDelay.flightdelayapi.weatherFactors.exception.InstrumentLandingSystemCalculationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstrumentLandingSystemCalculatorImpl implements InstrumentLandingSystemCalculator {

    private final RunwayWeatherCalculator runwayWeatherCalculator;

    public IlsCategory getMinRequiredCategory(AirportWeatherDto airportWeatherDto) {
        int rvrFt = getRunwayVisualRange(airportWeatherDto);

        List<IlsCategory> ilsCategoriesBasedOnRunway = new ArrayList<>();

        for (RunwayWeatherDto runway : airportWeatherDto.getRunwaysDTO()) {
            int cloudBaseFt = getCloudBaseFt(airportWeatherDto, runway.getAverageElevationFt());

            Optional<IlsCategory> category = Arrays.stream(IlsCategory.class.getEnumConstants())
                    .filter(ilsCategory -> areConditionsAboveTheLimits(
                            rvrFt, ilsCategory.getRunwayVisualRangeThresholdFt(),
                            cloudBaseFt, ilsCategory.getCloudBaseThresholdFt()))
                    .min(Comparator.comparing(IlsCategory::getValue));

            ilsCategoriesBasedOnRunway.add(category.orElse(null));
        }

        return ilsCategoriesBasedOnRunway
                .stream()
                .dropWhile(Objects::isNull)
                .min(Comparator.comparing(IlsCategory::getValue))
                .orElseThrow(InstrumentLandingSystemCalculationFailedException::new);
    }

    private boolean areConditionsAboveTheLimits(int rvrFt, int rvrThreshold, int cloudBaseFt, int cloudBaseThreshold) {
        return (rvrFt > rvrThreshold) && (cloudBaseFt > cloudBaseThreshold);
    }

    private int getCloudBaseFt(AirportWeatherDto airportWeatherDto, int elevationFt) {
        int elevation = UnitConverter.feetToMeters(elevationFt);
        int cloudBase = runwayWeatherCalculator.calculateCloudBase(
                airportWeatherDto.getWeather().getTemperature(),
                airportWeatherDto.getWeather().getDewPoint(),
                elevation);

        return UnitConverter.metersToFeet(cloudBase);
    }

    private int getRunwayVisualRange(AirportWeatherDto airportWeatherDto) {
        int runwayVisualRange = runwayWeatherCalculator.calculateRunwayVisualRange(
                airportWeatherDto.getWeather().getVisibilityM(),
                airportWeatherDto.getWeather().isDay());

        return UnitConverter.metersToFeet(runwayVisualRange);
    }
}
