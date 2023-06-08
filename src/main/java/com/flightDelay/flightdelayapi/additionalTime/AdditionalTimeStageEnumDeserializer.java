package com.flightDelay.flightdelayapi.additionalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.flightDelay.flightdelayapi.shared.exception.importData.InvalidAdditionalTimeStageEnumException;

import java.io.IOException;

public class AdditionalTimeStageEnumDeserializer extends JsonDeserializer<AdditionalTimeStage> {

    @Override
    public AdditionalTimeStage deserialize(JsonParser jsonParser,
                                   DeserializationContext deserializationContext) throws IOException {

        String enumValue = jsonParser.getValueAsString();
        AdditionalTimeStage[] enumValues = AdditionalTimeStage.values();

        for (AdditionalTimeStage value : enumValues) {
            if (value.name().equalsIgnoreCase(enumValue)) {
                return value;
            }
        }

        throw new InvalidAdditionalTimeStageEnumException(enumValue);
    }
}
