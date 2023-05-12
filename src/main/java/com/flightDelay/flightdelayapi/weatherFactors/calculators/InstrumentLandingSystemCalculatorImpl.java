package com.flightDelay.flightdelayapi.weatherFactors.calculators;
import com.flightDelay.flightdelayapi.weatherFactors.dtos.AirportWeatherDto;
import com.flightDelay.flightdelayapi.runway.RunwayDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
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

        for (RunwayDto runway : airportWeatherDto.getRunwaysDTO()) {
            int cloudBaseFt = getCloudBaseFt(airportWeatherDto, runway.getAverageElevationFt());

            Optional<IlsCategory> category = Arrays.stream(IlsCategory.class.getEnumConstants())
                    .filter(ilsCategory -> areConditionsAboveTheLimits(
                            rvrFt, ilsCategory.getRunwayVisualRangeThresholdFt(),
                            cloudBaseFt, ilsCategory.getCloudBaseThresholdFt()))
                    .min(Comparator.comparing(IlsCategory::getValue));

            ilsCategoriesBasedOnRunway.add(category.orElse(null));
        }

        Optional<IlsCategory> minRequiredILS = ilsCategoriesBasedOnRunway
                .stream()
                .dropWhile(Objects::isNull)
                .min(Comparator.comparing(IlsCategory::getValue));

        //TODO: Exception; Create custom exception and change type of this exception
        return minRequiredILS.orElseThrow(() -> new IllegalStateException("Unable to calculate ILS category"));
    }

    private boolean areConditionsAboveTheLimits(int rvrFt, int rvrThreshold, int cloudBaseFt, int cloudBaseThreshold) {
        return (rvrFt > rvrThreshold) && (cloudBaseFt > cloudBaseThreshold);
    }

    private int getCloudBaseFt(AirportWeatherDto airportWeatherDto, int elevationFt) {
        int elevation = UnitConverter.feetToMeters(elevationFt);
        int cloudBase = runwayWeatherCalculator.calculateCloudBase(airportWeatherDto, elevation);
        return UnitConverter.metersToFeet(cloudBase);
    }

    private int getRunwayVisualRange(AirportWeatherDto airportWeatherDto) {
        int runwayVisualRange = runwayWeatherCalculator.calculateRunwayVisualRange(
                airportWeatherDto.getWeather().getVisibilityM(), airportWeatherDto.getWeather().isDay());
        return UnitConverter.metersToFeet(runwayVisualRange);
    }
}
