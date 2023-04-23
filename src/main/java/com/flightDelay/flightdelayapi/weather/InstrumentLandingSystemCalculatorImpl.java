package com.flightDelay.flightdelayapi.weather;
import com.flightDelay.flightdelayapi.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.dto.RunwayDto;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
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

        int rvrFt = getRunwayVisualRange(airportWeatherDto);

        for (RunwayDto runway : airportWeatherDto.getRunwaysDTO()) {
            int cloudBaseFt = getCloudBaseFt(airportWeatherDto, runway.getAverageElevationFt());

            IlsCategory ilsCategory = null;

            if ((rvrFt > MAX_RVR_ILS_CATEGORY_1_IN_FT) && (cloudBaseFt > MAX_CLOUD_BASE_IN_FT)) {
                ilsCategory = IlsCategory.CATEGORY_0;

            } else if ((rvrFt > MAX_RVR_CATEGORY_2_IN_FT) && (cloudBaseFt > MAX_CLOUD_BASE_IN_FT)) {
                ilsCategory = IlsCategory.CATEGORY_1;

            } else if ((rvrFt > MAX_RVR_CATEGORY_3_IN_FT) && (cloudBaseFt > MIN_CLOUD_BASE_IN_FT)) {
                ilsCategory = IlsCategory.CATEGORY_2;

            } else if (rvrFt > MIN_RVR_CATEGORY_3_IN_FT) {
                ilsCategory = IlsCategory.CATEGORY_3A;
            }

            if (ilsCategory == null) {
                log.warn("Unable to calculate an ILS category for runway with id: {} in airport with id: {} " +
                        "when runway visual range is {} ft, and cloud base is {} ft", runway.getId(),
                        airportWeatherDto.getAirportIdent(), rvrFt, cloudBaseFt);

                continue;
            }

            ilsCategoriesBasedOnRunway.add(ilsCategory);
        }

        Optional<IlsCategory> minRequiredILS = ilsCategoriesBasedOnRunway
                .stream()
                .min(Comparator.comparing(IlsCategory::getValue));

        return minRequiredILS.orElseThrow(() -> new IllegalStateException("Unable to calculate ILS category"));
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
