package com.flightDelay.flightdelayapi.weather;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
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

        for (RunwayDto runway : airportWeatherDto.runwaysDTO()) {
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

    private int getRunwayVisualRange(AirportWeatherDto airportWeatherDto) {
        int runwayVisualRange = runwayWeatherCalculator.calculateRunwayVisualRange(
                airportWeatherDto.weather().getVisibilityM(), airportWeatherDto.weather().isDay());
        return UnitConverter.metersToFeet(runwayVisualRange);
    }
}
