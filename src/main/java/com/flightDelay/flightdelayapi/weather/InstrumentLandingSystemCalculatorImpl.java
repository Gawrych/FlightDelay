package com.flightDelay.flightdelayapi.weather;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InstrumentLandingSystemCalculatorImpl implements InstrumentLandingSystemCalculator {

    private static final int MAX_RVR_ILS_CATEGORY_1_IN_FT = 2500;
    private static final int MAX_RVR_CATEGORY_2_IN_FT = 1800;
    private static final int MAX_RVR_CATEGORY_3_IN_FT = 1200;
    private static final int MIN_RVR_CATEGORY_3_IN_FT = 700;
    private static final int MAX_CLOUD_BASE_IN_FT = 200;
    private static final int MIN_CLOUD_BASE_IN_FT = 100;

    private final WeatherCalculator weatherCalculator;

    public IlsCategory getCategory(AirportWeatherDto airportWeatherDto) {
        List<IlsCategory> ilsCategoriesBasedOnRunway = new ArrayList<>();

        for (RunwayDto runway : airportWeatherDto.getRunwaysDTO()) {
            int elevation = (int) Math.round(UnitConverter.feetToMeters(runway.getAverageElevationFt()));

            int runwayVisualRange = weatherCalculator.calculateRunwayVisualRange(airportWeatherDto.getVisibility(), airportWeatherDto.isDay());
            int cloudBase = weatherCalculator.calculateCloudBase(airportWeatherDto.getTemperature(), airportWeatherDto.getDewPoint(), elevation);

            int rvrFt = (int) Math.round(UnitConverter.metersToFeet(runwayVisualRange));
            int cloudBaseFt = (int) Math.round(UnitConverter.metersToFeet(cloudBase));

            IlsCategory ilsCategory = IlsCategory.UNKNOWN;

            if ((rvrFt > MAX_RVR_ILS_CATEGORY_1_IN_FT) && (cloudBaseFt > MAX_CLOUD_BASE_IN_FT)) {
                ilsCategory = IlsCategory.CATEGORY_0;

            } else if ((rvrFt > MAX_RVR_CATEGORY_2_IN_FT) && (cloudBaseFt > MAX_CLOUD_BASE_IN_FT)) {
                ilsCategory = IlsCategory.CATEGORY_1;

            } else if ((rvrFt > MAX_RVR_CATEGORY_3_IN_FT) && (cloudBaseFt > MIN_CLOUD_BASE_IN_FT)) {
                ilsCategory = IlsCategory.CATEGORY_2;

            } else if (rvrFt > MIN_RVR_CATEGORY_3_IN_FT) {
                ilsCategory = IlsCategory.CATEGORY_3A;

            }

            ilsCategoriesBasedOnRunway.add(ilsCategory);
        }

        Optional<IlsCategory> minRequiredILS = ilsCategoriesBasedOnRunway
                .stream()
                .dropWhile(ilsCategory -> ilsCategory == IlsCategory.UNKNOWN)
                .min(Comparator.comparing(IlsCategory::getValue));

        return minRequiredILS.orElse(IlsCategory.UNKNOWN);
    }
}
