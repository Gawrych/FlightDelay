package com.flightDelay.flightdelayapi.weather;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstrumentLandingSystemCalculatorImpl implements InstrumentLandingSystemCalculator {

    private final WeatherCalculator weatherCalculator;

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
        int cloudBase = weatherCalculator.calculateCloudBase(airportWeatherDto.getTemperature(), airportWeatherDto.getDewPoint(), elevation);
        return UnitConverter.metersToFeet(cloudBase);
    }

    private int getRunwayVisualRange(AirportWeatherDto airportWeatherDto) {
        int runwayVisualRange = weatherCalculator.calculateRunwayVisualRange(airportWeatherDto.getVisibility(), airportWeatherDto.isDay());
        return UnitConverter.metersToFeet(runwayVisualRange);
    }
}
