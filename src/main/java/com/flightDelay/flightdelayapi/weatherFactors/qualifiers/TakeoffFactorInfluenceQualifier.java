package com.flightDelay.flightdelayapi.weatherFactors.qualifiers;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.weatherFactors.properties.TakeoffLimitsProperties;
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
                    takeoffLimitsProperties.getLowerThresholdOfCrosswindKt(),
                    takeoffLimitsProperties.getUpperThresholdOfCrosswindKt());
        }

        return calculateRangeForValuesThatShouldBeSmall(factorValue,
                takeoffLimitsProperties.getLowerThresholdOfCrosswindInstrumentKt(),
                takeoffLimitsProperties.getUpperThresholdOfCrosswindInstrumentKt());
    }
}
