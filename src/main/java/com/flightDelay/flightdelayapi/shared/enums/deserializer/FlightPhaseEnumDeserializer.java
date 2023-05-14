package com.flightDelay.flightdelayapi.shared.enums.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.flightDelay.flightdelayapi.shared.exception.InvalidFlightPhaseEnumException;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FlightPhase;

import java.io.IOException;

public class FlightPhaseEnumDeserializer extends JsonDeserializer<FlightPhase> {

    @Override
    public FlightPhase deserialize(JsonParser jsonParser,
                                   DeserializationContext deserializationContext) throws IOException {

        String enumValue = jsonParser.getValueAsString();
        FlightPhase[] enumValues = FlightPhase.values();

        for (FlightPhase value : enumValues) {
            if (value.name().equalsIgnoreCase(enumValue)) {
                return value;
            }
        }

        throw new InvalidFlightPhaseEnumException(enumValue);
    }
}
