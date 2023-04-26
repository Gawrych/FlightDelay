package com.flightDelay.flightdelayapi.weather;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.IlsCategory;
import com.flightDelay.flightdelayapi.weather.properties.TakeoffLimitsProperties;
import org.springframework.stereotype.Component;

@Component("takeoffFactorInfluenceQualifier")
public class TakeoffFactorInfluenceQualifier extends FlightPhaseFactorInfluenceQualifierImpl {

    private final TakeoffLimitsProperties takeoffLimitsProperties;

    public TakeoffFactorInfluenceQualifier(TakeoffLimitsProperties takeoffLimitsProperties) {
        super(takeoffLimitsProperties);
        this.takeoffLimitsProperties = takeoffLimitsProperties;
    }

    @Override
    public FactorInfluence checkCrosswindLimits(IlsCategory ilsCategory, int factorValue) {
        if (ilsCategory.getValue() == IlsCategory.NONPRECISION.getValue()) {
            return calculateRangeForValuesThatShouldBeSmall(factorValue,
                    takeoffLimitsProperties.getLowerThresholdOfCrosswindKts(),
                    takeoffLimitsProperties.getUpperThresholdOfCrosswindKts());
        }

        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                takeoffLimitsProperties.getLowerThresholdOfCrosswindInstrumentKts(),
                takeoffLimitsProperties.getUpperThresholdOfCrosswindInstrumentKts());
    }
}
