package com.flightDelay.flightdelayapi.weather;
import com.flightDelay.flightdelayapi.shared.UnitConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstrumentLandingSystemCalculatorImpl implements InstrumentLandingSystemCalculator {

    private static final int MAX_RVR_CATEGORY_1 = 4000;
    private static final int MAX_RVR_CATEGORY_2 = 1800;
    private static final int MAX_RVR_CATEGORY_3 = 1200;
    private static final int MIN_RVR_CATEGORY_3 = 700;
    private static final int MAX_CLOUD_BASE = 200;
    private static final int MIN_CLOUD_BASE = 100;

    public int getCategory(Weather weather, int elevationFt) {
        int elevation = (int) Math.round(UnitConverter.feetToMeters(elevationFt));
        int rvr = calculateRunwayVisualRange(weather.getVisibility(), weather.isDay());
        int cloudBase = calculateCloudBase(weather.getTemperature(), weather.getDewPoint(), elevation);

        int rvrFt = (int) Math.round(UnitConverter.metersToFeet(rvr));
        int cloudBaseFt = (int) Math.round(UnitConverter.metersToFeet(cloudBase));

        if ((rvrFt > MAX_RVR_CATEGORY_1) && (cloudBaseFt > MAX_CLOUD_BASE)) {
            return 0;
        } else if ((rvrFt <= MAX_RVR_CATEGORY_1 && rvrFt > MAX_RVR_CATEGORY_2) && (cloudBaseFt > MAX_CLOUD_BASE)) {
            return 1;
        } else if ((rvrFt <= MAX_RVR_CATEGORY_2 && rvrFt > MAX_RVR_CATEGORY_3) && (cloudBaseFt < MAX_CLOUD_BASE && cloudBaseFt > MIN_CLOUD_BASE)) {
            return 2;
        } else if ((rvrFt <= MAX_RVR_CATEGORY_3 && rvrFt > MIN_RVR_CATEGORY_3) && (cloudBaseFt > MIN_CLOUD_BASE)) {
            return 3;
        } else {
            return 4;
        }
    }

    public int calculateRunwayVisualRange(float visibility, boolean isDay) {
        double multiplier = 1.6;
        if (isDay) {
            multiplier = 1.3;
        }
        return (int) Math.round((visibility * multiplier));
    }

    public int calculateCloudBase(float temperature, float dewPoint, int elevation) {
        return Math.round((temperature - dewPoint) / 10 * 1247 + elevation);
    }

}
